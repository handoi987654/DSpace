package com.Desert.Service;

import com.Desert.Entity.IDea.IDea;
import com.Desert.Entity.Project.Project;
import com.Desert.Repository.HomeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class HomeServiceBean implements HomeService {

    @Autowired
    private HomeRepo homeRepo;

    @Override
    public List<Project> getProjects() {
        return homeRepo.getProjects();
    }

    @Override
    public void insertProject(Project project) {
        homeRepo.insertProject(project);
    }

    @Override
    public List<IDea> getIDeas() {
        return homeRepo.getIDeas();
    }

    @Override
    public void insertIDea(IDea iDea) {
        homeRepo.insertIDea(iDea);
    }
}
