# !/usr/bin/env python3
# _*_ coding:utf-8 _*_
"""
    @Time   : 2021/5/26 9:14
    @Author : zhanglexiao
    @File   : pred.py
"""

import pandas as pd
from deepkt import data_util, deepkt, metrics
import redis
import happybase


def pre_dataset(student_id, r):
    data = []
    li = r.lrange(student_id, 0, -1)
    if len(li) <= 1:
        print("没有做题记录，无法预测")
    di = []
    for row in li:
        s = row.split(',')
        feat = int(s[0]) + int(s[1]) * 2
        s.append(feat)
        di.append(s)
    df = pd.DataFrame(di, columns=['correct', 'skill_id', 'skill_with_answer'])
    df['correct'] = df['correct'].astype(dtype=int)
    df['skill_id'] = df['skill_id'].astype(dtype=int)
    data.append(tuple((df['skill_with_answer'].values[:-1], df['skill_id'].values[1:], df['correct'].values[1:])))
    seq = pd.Series(data, index=[student_id])
    nb_users = len(seq)
    dataset, length, nb_features, nb_skills = data_util.load_dataset(data, batch_size=1)
    return dataset


def prap_model():
    nb_features = 181
    nb_skills = 92
    best_model_weights = "weights/bestmodel"  # File to save the model.
    log_dir = "logs"  # Path to save the logs.
    optimizer = "adam"  # Optimizer to use
    lstm_units = 50  # Number of LSTM units
    epochs = 100  # Number of epochs to train
    dropout_rate = 0.3
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
    student_model.load_weights(best_model_weights)
    return student_model


if __name__ == '__main__':
    pool = redis.ConnectionPool(host='ubuntu1', port=6379, decode_responses=True)
    r = redis.Redis(host='ubuntu1', port=6379, decode_responses=True)

    conn = happybase.Connection("ubuntu1", port=9090)
    table = happybase.Table('student', conn)
    scanner = table.scan()
    for key, value in scanner:
        key = key.decode('utf8')
        print(key, value)
        if key[0] != 'U':
            continue
        test_data = pre_dataset(key, r)
        res = student_model.predict(test_data)[0][-1]
        save_res = [round(x, 2) for x in res]
        table.put(key, {'info:concepts': str(save_res)})

        # for index in value.keys():  # 遍历字典
        #     print(key, index.decode('utf-8'), value[index].decode('utf-8'))
