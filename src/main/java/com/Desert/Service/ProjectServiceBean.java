package com.Desert.Service;

import com.Desert.Entity.Project.Project;
import com.Desert.Entity.Project.ProjectNote;
import com.Desert.Repository.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProjectServiceBean implements ProjectService {

    @Autowired
    private ProjectRepo projectRepo;

    @Override
    public List<ProjectNote> getNoteList(Project project) {
        return projectRepo.getNoteList(project);
    }
}
