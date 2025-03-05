package com.webcuoiky.softwareshop.services;

import com.webcuoiky.softwareshop.model.Software;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SoftwareRepositoryPaused extends JpaRepository<Software, Integer> {

}
