package com.cafe.myutils.sprocess.impl.processes;



import com.cafe.myutils.audit.AuditBusinessObject;
import com.cafe.myutils.service.IService;
import com.cafe.myutils.sprocess.impl.AbstractProcessImpl;

import java.util.List;

public abstract class AbstractDeleteProcessImpl<T extends AuditBusinessObject, S extends IService<T>> extends AbstractProcessImpl<T, S> {
    protected AbstractDeleteProcessImpl(S service) {
        super(service);
        this.configure();
    }

    protected abstract void process(T item);

    protected void process(Long id) {
        T item = service.findById(id);
        if (item != null) process(item);
    }

    public void run(T item) {
        process(item);
        deleteAssociatedList(item);
        service.delete(item);
    }

    public void run(Long id) {
        process(id);
        T item = service.findById(id);
        if (item != null) run(item);
    }

    public void runByIds(List<Long> ids) {
        ids.forEach(this::process);
        service.deleteByIdIn(ids);
    }

    public void run(List<T> items) {
        items.forEach(this::process);
        service.delete(items);
    }

    public void deleteAssociatedList(T item) {
    }

    public void deleteWithAssociatedList(T item) {
        deleteAssociatedList(item);
        run(item);
    }

    public void deleteWithAssociatedList(List<T> items) {
        items.forEach(this::deleteAssociatedList);
        run(items);
    }

    public void deleteWithAssociatedList(Long id) {
        T item = service.findById(id);
        if (item != null) {
            deleteAssociatedList(item);
            run(id);
        }
    }

    public void deleteWithAssociatedListByIds(List<Long> ids) {
        ids.forEach(id -> {
            T item = service.findById(id);
            if (item != null) {
                deleteAssociatedList(item);
            }
        });
        service.deleteByIdIn(ids);
    }
}
