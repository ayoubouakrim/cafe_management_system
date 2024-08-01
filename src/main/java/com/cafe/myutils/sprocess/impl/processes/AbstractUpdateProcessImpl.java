package com.cafe.myutils.sprocess.impl.processes;


import com.cafe.myutils.audit.AuditBusinessObject;
import com.cafe.myutils.service.IService;
import com.cafe.myutils.sprocess.impl.AbstractProcessImpl;

import java.util.List;

public abstract class AbstractUpdateProcessImpl<T extends AuditBusinessObject, S extends IService<T>> extends AbstractProcessImpl<T, S> {
    protected AbstractUpdateProcessImpl(S service) {
        super(service);
        this.configure();
    }

    public abstract T run(T item);

    public List<T> run(List<T> items) {
        items.forEach(this::run);
        return items;
    }

    public void updateAssociatedList(T item) {
    }
}
