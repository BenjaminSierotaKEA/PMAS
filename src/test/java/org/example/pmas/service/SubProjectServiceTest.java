package org.example.pmas.service;

import org.example.pmas.model.SubProject;
import org.example.pmas.modelBuilder.MockDataModel;
import org.example.pmas.repository.SubProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubProjectServiceTest {

    @Mock
    private SubProjectRepository subprojectRepository;

    @InjectMocks
    private SubProjectService subprojectService;

    private List<SubProject> subprojects;

    @BeforeEach
    void setUp() {
        subprojects = MockDataModel.subprojectsWithValues();
    }

    @Test
    void getAllSubProjects() {
        when(subprojectRepository.readAll()).thenReturn(subprojects);

        List<SubProject> actualSubProjects = subprojectService.readAll();

        assertNotNull(actualSubProjects);
        assertEquals(subprojects, actualSubProjects);
        assertEquals(actualSubProjects.size(), subprojects.size());

        verify(subprojectRepository).readAll();
    }
}
