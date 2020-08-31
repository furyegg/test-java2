package com.qiniu.interview.T03_designpattern;

public interface UserSerializer {
    void writeStart();
    
    void writeName();
    
    void writeAge();
    
    void writeEnd();
}