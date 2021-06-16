# !/usr/bin/env python3
# _*_ coding:utf-8 _*_
"""
    @Time   : 2021/5/23 16:22
    @Author : zhanglexiao
    @File   : train.py
"""
from deepkt import deepkt, data_util, metrics
import tensorflow as tf
import os
import pandas as pd
import numpy as np
import tensorflow as tf
from deepkt import data_util, deepkt, metrics
import redis

if __name__ == '__main__':
    pool = redis.ConnectionPool(host='ubuntu1', port=6379, decode_responses=True)
    r = redis.Redis(host='ubuntu1', port=6379, decode_responses=True)
    data = []
    students = r.keys()
    max_feat = 0
    for student_id in students:
        try:
            li = r.lrange(student_id, 0, -1)
            if len(li) <= 1:
                continue
            di = []
            for row in li:
                s = row.split(',')
                feat = int(s[0]) + int(s[1]) * 2
                if feat > max_feat:
                    max_feat = feat
                s.append(feat)
                di.append(s)
            df = pd.DataFrame(di, columns=['correct', 'skill_id', 'skill_with_answer'])
            df['correct'] = df['correct'].astype(dtype=int)
            df['skill_id'] = df['skill_id'].astype(dtype=int)
            data.append(
                tuple((df['skill_with_answer'].values[:-1], df['skill_id'].values[1:], df['correct'].values[1:])))
        except:
            students.remove(student_id)
            continue
    seq = pd.Series(data, index=students)
    nb_users = len(seq)
    batch_size = 32
    MASK_VALUE = -1.
    verbose = 1  # Verbose = {0,1,2}
    best_model_weights = "weights/bestmodel"  # File to save the model.
    log_dir = "logs"  # Path to save the logs.
    optimizer = "adam"  # Optimizer to use
    lstm_units = 50  # Number of LSTM units
    epochs = 100  # Number of epochs to train
    dropout_rate = 0.3
    dataset, length, nb_features, nb_skills = data_util.load_dataset(data,
                                                                     batch_size=batch_size)

    test_fraction = 0.1
    validation_fraction =0.1
    train_set, test_set, val_set = data_util.split_dataset(dataset=dataset,
                                                       total_size=length,
                                                       test_fraction=test_fraction,
                                                       val_fraction=validation_fraction)

    set_sz = length * batch_size
    test_set_sz = (set_sz * test_fraction)
    val_set_sz = (set_sz - test_set_sz) * validation_fraction
    train_set_sz = set_sz - test_set_sz - val_set_sz
    print("============= Data Summary =============")
    print("Total number of students: %d" % set_sz)
    print("Training set size: %d" % train_set_sz)
    print("Validation set size: %d" % val_set_sz)
    print("Testing set size: %d" % test_set_sz)
    print("Number of skills: %d" % nb_skills)
    print("Number of features in the input: %d" % nb_features)
    print("========================================")

    student_model = deepkt.DKTModel(
        nb_features=nb_features,
        nb_skills=nb_skills,
        hidden_units=lstm_units,
        dropout_rate=dropout_rate)
    student_model.compile(
        optimizer=optimizer,
        metrics=[
            metrics.BinaryAccuracy(),
            metrics.AUC(),
            metrics.Precision(),
            metrics.Recall()
        ])
    print(student_model.summary())
    history = student_model.fit(dataset=train_set,
                                epochs=epochs,
                                verbose=verbose,
                                validation_data=val_set,
                                callbacks=[
                                    tf.keras.callbacks.CSVLogger(f"{log_dir}/train.log"),
                                    tf.keras.callbacks.ModelCheckpoint(best_model_weights,
                                                                       save_best_only=True,
                                                                       save_weights_only=True),
                                    tf.keras.callbacks.TensorBoard(log_dir=log_dir)
                                ])