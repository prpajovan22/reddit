package com.ftn.reddit.repositorys;

import com.ftn.reddit.model.Comment;
import com.ftn.reddit.model.Post;
import com.ftn.reddit.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

    boolean existsByPostAndAccepted(Post post, boolean accepted);

    List<Report> findByAccepted(boolean accepted);

}
