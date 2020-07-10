package com.sayapatri.parasi.Repository;

import com.sayapatri.parasi.Model.feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepositry extends JpaRepository<feedback,Long>
{
}
