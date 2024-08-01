package com.cafe.myutils.sprocess.impl;


import com.cafe.myutils.audit.AuditBusinessObject;
import com.cafe.myutils.service.IService;

public abstract class AbstractProcessImpl<T extends AuditBusinessObject, S extends IService<T>> {
    protected S service;
    protected Class<T> itemClass;

    protected AbstractProcessImpl(S service) {
        this.service = service;
        this.configure();
    }

    public void configure(Class<T> itemClass) {
        this.itemClass = itemClass;
    }


    public abstract void configure();
}
