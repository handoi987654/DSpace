package com.Desert.Repository;

import com.Desert.Entity.IDea.IDea;
import com.Desert.Entity.IDea.IDeaNote;
import com.Desert.Entity.IDea.IDeaResourceGroup;
import com.Desert.Entity.IDea.IDeaTask;

import java.util.List;

public interface IDeaRepo {

    List<IDeaTask> getTaskList(IDea iDea);

    int insertTask(IDeaTask task);

    void updateTask(IDeaTask task);

    void deleteTask(IDeaTask task);

    List<IDeaNote> getNoteList(IDea iDea);

    int insertResources(IDeaResourceGroup group);
}
