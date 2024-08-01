package com.cafe.myutils.sprocess.impl;



import com.cafe.myutils.audit.AuditBusinessObject;
import com.cafe.myutils.sprocess.facade.ICreateProcess;
import com.cafe.myutils.sprocess.facade.IDeleteProcess;
import com.cafe.myutils.sprocess.facade.IUpdateProcess;
import com.cafe.myutils.util.ListUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

public class ProcessHelper {
    private ProcessHelper() {
    }

    public static <T extends AuditBusinessObject, L extends AuditBusinessObject> List<List<L>> getToBeSavedAndToBeDeleted(List<L> oldList, List<L> newList) {
        boolean oldEmpty = ListUtil.isEmpty(oldList);
        boolean newEmpty = ListUtil.isEmpty(newList);

        List<List<L>> result = new ArrayList<>();
        List<L> resultDelete = new ArrayList<>();
        List<L> resultUpdateOrSave = new ArrayList<>();

        if (!newEmpty && oldEmpty) {
            resultUpdateOrSave.addAll(newList);
        } else if (newEmpty && !oldEmpty) {
            resultDelete.addAll(oldList);
        } else if (!newEmpty) {
            populateSaveOrUpdate(oldList, newList, resultUpdateOrSave);
            separateToDeleteAndToUpdate(oldList, newList, resultUpdateOrSave, resultDelete);
        }

        result.add(resultUpdateOrSave);
        result.add(resultDelete);
        return result;
    }

    private static <T extends AuditBusinessObject, L extends AuditBusinessObject> void populateSaveOrUpdate(List<L> oldList, List<L> newList, List<L> resultUpdateOrSave) {
        newList.forEach(myNew -> {
            L t = ListUtil.findFirstEquals(oldList, myNew);
            if (t == null) {
                resultUpdateOrSave.add(myNew);
            }
        });
    }

    private static <T extends AuditBusinessObject, L extends AuditBusinessObject> void separateToDeleteAndToUpdate(List<L> oldList, List<L> newList, List<L> resultUpdateOrSave, List<L> resultDelete) {
        oldList.forEach(myOld -> {
            L t = ListUtil.findFirstEquals(newList, myOld);
            if (t != null) {
                resultUpdateOrSave.add(t);
            } else {
                resultDelete.add(myOld);
            }
        });
    }

    public static <T extends AuditBusinessObject, L extends AuditBusinessObject, CP extends ICreateProcess<L>> void createList(
            T entity,
            Function<T, List<L>> getter,
            BiConsumer<L, T> function,
            CP createProcess) {
        List<L> list = getter.apply(entity);
        if (list != null) {
            list.forEach(el -> {
                function.accept(el, entity);
                createProcess.run(el);
            });
        }
    }

    public static <
            T extends AuditBusinessObject,
            L extends AuditBusinessObject,
            UP extends IUpdateProcess<L>,
            DP extends IDeleteProcess<L>
            >
    void updateList(
            T t,
            LongFunction<List<L>> getOldList,
            Function<T, List<L>> getNewList,
            BiFunction<List<L>, List<L>, List<List<L>>> listsFilter,
            Consumer<L> applyBeforeUpdating,
            UP updateProcess,
            DP deleteProcess
    ) {
        List<List<L>> result = listsFilter.apply(getOldList.apply(t.getId()), getNewList.apply(t));
        deleteProcess.run(result.get(1));
        ListUtil.emptyIfNull(result.get(0)).forEach(applyBeforeUpdating);
        updateProcess.run(result.get(0));
    }

    public static <
            T extends AuditBusinessObject,
            L extends AuditBusinessObject,
            UP extends IUpdateProcess<L>,
            DP extends IDeleteProcess<L>
            >
    void updateList(
            T t,
            LongFunction<List<L>> getOldList,
            Function<T, List<L>> getNewList,
            BiConsumer<L, T> setItem,
            UP updateProcess,
            DP deleteProcess
    ) {
        updateList(
                t, getOldList, getNewList,
                ProcessHelper::getToBeSavedAndToBeDeleted,
                item -> setItem.accept(item, t),
                updateProcess, deleteProcess
        );
    }
}
