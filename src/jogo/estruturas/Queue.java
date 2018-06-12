/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo.estruturas;

/**
 *
 * @author inmp
 */
public class Queue<T> extends SQBase<T> {

    @Override
    public void add(T e) {
        linkedList.addFirst(e);
    }

    @Override
    public T get() {
        T e = linkedList.getLast();
        if (e != null) {
            linkedList.remove(e);
        }
        return e;
    }

}
