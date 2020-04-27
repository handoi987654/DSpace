package com.Desert.Service;

import com.Desert.Entity.IDea.IDea;
import com.Desert.Entity.IDea.IDeaNote;
import com.Desert.Entity.IDea.IDeaResourceGroup;
import com.Desert.Entity.IDea.IDeaTask;
import com.Desert.Repository.IDeaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class IDeaServiceBean implements IDeaService {

    @Autowired
    private IDeaRepo repo;

    @Override
    public List<IDeaTask> getTaskList(IDea iDea) {
        return repo.getTaskList(iDea);
    }

    @Override
    public int insertTask(IDeaTask task) {
        return repo.insertTask(task);
    }

    @Override
    public void updateTask(IDeaTask task) {
        repo.updateTask(task);
    }

    @Override
    public void deleteTask(IDeaTask task) {
        repo.deleteTask(task);
    }

    @Override
    public List<IDeaNote> getNoteList(IDea iDea) {
        return repo.getNoteList(iDea);
    }

    @Override
    public int insertResources(IDeaResourceGroup group) {
        return repo.insertResources(group);
    }
}
