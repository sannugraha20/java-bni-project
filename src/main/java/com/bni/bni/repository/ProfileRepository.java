package com.bni.bni.repository;

// Update the import below to the correct package for Profile entity
import com.bni.bni.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}