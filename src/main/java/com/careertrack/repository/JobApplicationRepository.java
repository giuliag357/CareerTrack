package com.careertrack.repository;

import com.careertrack.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    // SELECT * FROM job_applications WHERE user_id = ? ORDER BY created_at DESC
    List<JobApplication> findAllByUserIdOrderByCreatedAtDesc(Long userId);

    // SELECT * FROM job_applications WHERE id = ? AND user_id = ?
    Optional<JobApplication> findByIdAndUserId(Long id, Long userId);
}