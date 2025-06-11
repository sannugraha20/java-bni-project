package com.bni.bni.service;

import com.bni.bni.entity.Profile;
import com.bni.bni.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.ZonedDateTime;

@Service
public class ProfileService {

    private final ProfileRepository repository;

    public ProfileService(ProfileRepository repository) {
        this.repository = repository;
    }

    public List<Profile> getAllProfiles() {
        return repository.findAll();
    }

    public Optional<Profile> getProfileById(Long id) {
        return repository.findById(id);
    }

    public Profile createProfile(Profile profile) {
        return repository.save(profile);
    }

    public Profile updateProfile(Long id, Profile updatedProfile) {
        return repository.findById(id).map(profile -> {
            profile.setUserId(updatedProfile.getUserId());
            profile.setFirstName(updatedProfile.getFirstName());
            profile.setLastName(updatedProfile.getLastName());
            profile.setPlaceOfBirth(updatedProfile.getPlaceOfBirth());
            profile.setDateOfBirth(updatedProfile.getDateOfBirth());
            profile.setUpdatedAt(ZonedDateTime.now());
            return repository.save(profile);
        }).orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    public void deleteProfile(Long id) {
        repository.deleteById(id);
    }
}