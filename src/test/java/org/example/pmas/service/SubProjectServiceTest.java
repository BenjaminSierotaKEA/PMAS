package org.example.pmas.service;

import org.example.pmas.model.SubProject;
import org.example.pmas.modelBuilder.MockDataModel;
import org.example.pmas.repository.ProjectRepository;
import org.example.pmas.repository.SubProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubProjectServiceTest {

    @Mock
    private SubProjectRepository subprojectRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private SubProjectService subprojectService;

    private List<SubProject> subprojects;

    @BeforeEach
    void setUp() {
        subprojects = MockDataModel.subprojectsWithValues();
    }

    @Test
    void getSubProject() {
        when(subprojectRepository.readSelected(1)).thenReturn(subprojects.getFirst());

        SubProject result = subprojectService.readSelected(1);

        assertNotNull(result);
        assertEquals(subprojects.getFirst(), result);

        verify(subprojectRepository).readSelected(1);
    }

    @Test
    void createSubProject() {
        SubProject test = new SubProject(6,"test","test");
        test.setProjectID(1);
        when(projectRepository.doesProjectExist(1)).thenReturn(true);
        when(subprojectRepository.create(test)).thenReturn(test);

        SubProject result = subprojectService.create(test);

        assertNotNull(result);
        assertEquals(test, result);

        verify(subprojectRepository).create(test);
    }

    @Test
    void deleteSubProject() {
        SubProject delete = MockDataModel.subprojectsWithValues().getFirst();
        int id = delete.getId();
        when(subprojectRepository.doesSubProjectExist(1)).thenReturn(true);
        when(subprojectRepository.delete(delete.getId())).thenReturn(true);

        subprojectService.delete(delete.getId());

        verify(subprojectRepository,times(1)).doesSubProjectExist(id);
        verify(subprojectRepository,times(1)).delete(id);
    }

}
