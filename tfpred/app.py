import pandas as pd
# from deepkt import data_util, deepkt, metrics
import redis
import happybase
import pred
from flask import Flask, render_template, request, redirect, url_for
from werkzeug.utils import secure_filename
from werkzeug.datastructures import FileStorage
import os

app = Flask(__name__)

pool = redis.ConnectionPool(host='ubuntu1', port=6379, decode_responses=True)
r = redis.Redis(host='ubuntu1', port=6379, decode_responses=True)
conn = happybase.Connection("ubuntu1", port=9090)
table = happybase.Table('problem', conn)
model = pred.prap_model()

code2consept = {0: 'B树', 1: 'C++', 2: 'EM算法', 3: 'IO', 4: 'IP', 5: 'KMP', 6: 'LR(k)方法', 7: 'Linux', 8: 'Python',
                9: 'SimpleGUITk', 10: 'TCP协议', 11: 'java', 12: '一阶谓词演算', 13: '中间代码', 14: '二叉树', 15: '人工智能', 16: '以太网',
                17: '伸展树', 18: '信号', 19: '内存', 20: '函数', 21: '函数调用', 22: '列表', 23: '刚度', 24: '力', 25: '协议', 26: '向量',
                27: '命题', 28: '哈希', 29: '堆', 30: '复杂度', 31: '字符串', 32: '容器', 33: '寄存器', 34: '序列', 35: '应变', 36: '异常',
                37: '弹性', 38: '循环', 39: '微分', 40: '快速排序', 41: '感知机', 42: '指针', 43: '排列', 44: '排序', 45: '推理', 46: '搜索',
                47: '操作系统', 48: '支持向量机', 49: '收敛', 50: '数据结构', 51: '文件', 52: '文法', 53: '朴素贝叶斯', 54: '机器学习', 55: '条件语句',
                56: '极限', 57: '构造函数', 58: '栈', 59: '树', 60: '概率', 61: '正则表达式', 62: '母函数', 63: '汇编', 64: '派生类',
                65: '物理层', 66: '用户', 67: '神经网络', 68: '积分', 69: '移动网络', 70: '程序设计', 71: '符号表', 72: '算术运算符', 73: '类',
                74: '红黑树', 75: '组合数学', 76: '编译', 77: '网络', 78: '群', 79: '聚类', 80: '计算', 81: '语法', 82: '语法分析', 83: '进程',
                84: '进程和线程', 85: '逆波兰表示法', 86: '逻辑', 87: '逻辑斯谛', 88: '重载', 89: '面向对象', 90: '马尔可夫'}
consept2code = {'B树': 0, 'C++': 1, 'EM算法': 2, 'IO': 3, 'IP': 4, 'KMP': 5, 'LR(k)方法': 6, 'Linux': 7, 'Python': 8,
                'SimpleGUITk': 9, 'TCP协议': 10, 'java': 11, '一阶谓词演算': 12, '中间代码': 13, '二叉树': 14, '人工智能': 15, '以太网': 16,
                '伸展树': 17, '信号': 18, '内存': 19, '函数': 20, '函数调用': 21, '列表': 22, '刚度': 23, '力': 24, '协议': 25, '向量': 26,
                '命题': 27, '哈希': 28, '堆': 29, '复杂度': 30, '字符串': 31, '容器': 32, '寄存器': 33, '序列': 34, '应变': 35, '异常': 36,
                '弹性': 37, '循环': 38, '微分': 39, '快速排序': 40, '感知机': 41, '指针': 42, '排列': 43, '排序': 44, '推理': 45, '搜索': 46,
                '操作系统': 47, '支持向量机': 48, '收敛': 49, '数据结构': 50, '文件': 51, '文法': 52, '朴素贝叶斯': 53, '机器学习': 54, '条件语句': 55,
                '极限': 56, '构造函数': 57, '栈': 58, '树': 59, '概率': 60, '正则表达式': 61, '母函数': 62, '汇编': 63, '派生类': 64,
                '物理层': 65, '用户': 66, '神经网络': 67, '积分': 68, '移动网络': 69, '程序设计': 70, '符号表': 71, '算术运算符': 72, '类': 73,
                '红黑树': 74, '组合数学': 75, '编译': 76, '网络': 77, '群': 78, '聚类': 79, '计算': 80, '语法': 81, '语法分析': 82, '进程': 83,
                '进程和线程': 84, '逆波兰表示法': 85, '逻辑': 86, '逻辑斯谛': 87, '重载': 88, '面向对象': 89, '马尔可夫': 90}


@app.route('/')
def hello_world():
    return 'Hello World!'


@app.route('/upload', methods=['POST', 'GET'])
def upload():
    if request.method == 'POST':
        f = request.files['file']
        ff = FileStorage(f)
        df = pd.read_json(ff, lines=True, encoding='utf-8')
        save_res = []
        for i in range(len(df)):
            # 根据problem查到知识点
            skill = eval(table.cells(df.iloc[i]['problem_id'], 'info:concept')[0].decode('utf8'))[0]
            skill_id = consept2code[skill]
            test_data = pred.pre_dataset(df.iloc[i]['student_id'], r)
            res = model.predict(test_data)[0][-1]
            save_res.append(round(res[skill_id], 2))
        print(save_res)

    return render_template('upload.html')


@app.route('/test', methods=['POST', 'GET'])
def test():
    data = []
    true_r = 0
    tp = 0
    rp = 0
    all_p = 0
    if request.method == 'POST':
        f = request.files['file']
        print(request.form.get('threshold'))
        th = eval(request.form.get('threshold'))
        ff = FileStorage(f)
        df = pd.read_json(ff, lines=True, encoding='utf-8')
        save_res = []

        true_r = sum(df['label'].values)
        for i in range(len(df)):
            # 根据problem查到知识点
            print(df.iloc[i]['student_id'])
            skill = eval(table.cells(df.iloc[i]['problem_id'], 'info:concept')[0].decode('utf8'))[0]
            skill_id = consept2code[skill]
            test_data = pred.pre_dataset(df.iloc[i]['student_id'], r)
            res = model.predict(test_data)[0][-1]
            save_res.append(round(res[skill_id], 2))
            if res[skill_id] > th:
                rp += 1
                if df.iloc[i]['label'] == 1:
                    tp += 1
            else:
                if df.iloc[i]['label'] == 0:
                    tp += 1
            s = [df.iloc[i]['student_id'], df.iloc[i]['problem_id'], df.iloc[i]['label'], round(res[skill_id], 2)]
            data.append(s)
        all_p = len(data)
    if all_p == 0:
        all_p = 1
    return render_template("quizbase.html", data=data, rp=rp, true_r=true_r, all_p=all_p, tp=tp)


if __name__ == '__main__':
    app.run()
