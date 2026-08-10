package hn.courseservice.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import hn.courseservice.entity.Course;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByTenMonHocIgnoreCase(String tenMonHoc);
    Page<Course> findByTenMonHocContainingIgnoreCase(String keyword, Pageable pageable);
}