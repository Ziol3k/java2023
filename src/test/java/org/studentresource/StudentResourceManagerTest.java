package org.studentresource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentResourceManagerTest {
    private StudentResourceManager<Course> courseManager;
    private StudentResourceManager<StudyMaterial> materialManager;

    @BeforeEach
    void setUp() {
        courseManager = new StudentResourceManager<>();
        materialManager = new StudentResourceManager<>();
    }

    @Test
    void addAndRetrieveCourseTest() {
        Course course = new Course("CS101", "Introduction to Computer Science");
        courseManager.addResource(course);

        Course retrieved = courseManager.getResource("CS101");
        assertNotNull(retrieved, "Retrieved course should not be null.");
        assertEquals("Introduction to Computer Science", retrieved.getName(), "Course name should match.");
    }

    @Test
    void removeCourseTest() {
        Course course = new Course("CS101", "Introduction to Computer Science");
        courseManager.addResource(course);

        // Test removing courses
        courseManager.removeResource(course);

        Course retrieved = courseManager.getResource("CS101");
        assertNull(retrieved, "Retrieved course should be null after removal.");
    }

    @Test
    void addAndRetrieveStudyMaterialTest() {
        StudyMaterial material = new StudyMaterial("MATH101", "Basic Mathematics");
        materialManager.addResource(material);

        StudyMaterial retrieved = materialManager.getResource("MATH101");
        assertNotNull(retrieved, "Retrieved study material should not be null.");
        assertEquals("Basic Mathematics", retrieved.getName(), "Study material name should match.");
    }

    @Test
    void removeStudyMaterialTest() {
        StudyMaterial material = new StudyMaterial("MATH101", "Basic Mathematics");
        materialManager.addResource(material);

        // Test removing study materials
        materialManager.removeResource(material);

        StudyMaterial retrieved = materialManager.getResource("MATH101");
        assertNull(retrieved, "Retrieved study material should be null after removal.");
    }

}
