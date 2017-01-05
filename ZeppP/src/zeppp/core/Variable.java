package zeppp.core;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Samuel
 */
public interface Variable<T> {
    T get();
    T min();
    T max();
    Variable<T> clone();
    default void set(T min, T max){}
}
