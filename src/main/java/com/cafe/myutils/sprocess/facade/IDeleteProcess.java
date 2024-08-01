package com.cafe.myutils.sprocess.facade;

import java.util.List;

public interface IDeleteProcess<T> {
    void run(T item);

    void run(Long id);

    void runByIds(List<Long> ids);

    void run(List<T> items);

    void deleteWithAssociatedList(T t);

    void deleteWithAssociatedList(List<T> ts);

    void deleteWithAssociatedList(Long id);

    void deleteWithAssociatedListByIds(List<Long> ids);
}
