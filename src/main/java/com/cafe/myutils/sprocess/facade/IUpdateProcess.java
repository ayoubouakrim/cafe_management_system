package com.cafe.myutils.sprocess.facade;

import java.util.List;

public interface IUpdateProcess<T> {
    T run(T item);

    List<T> run(List<T> items);
}
