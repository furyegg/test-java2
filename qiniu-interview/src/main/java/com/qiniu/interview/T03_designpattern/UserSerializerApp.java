package com.qiniu.interview.T03_designpattern;

/**
 * 转化一个对象为XML或Json格式，调用者可以选择输出为单行文本 或者 输出为格式化后的文本</br>
 * 假设不使用现成框架输出，需要自己实现，但是不需要关心具体实现<br/>
 * 主要考虑调用者如何可以灵活选择不同的输出方式 和 是否格式化输出
 */
public class UserSerializerApp {
    public static void main(String[] args) {
        User user = new User();
    }
}