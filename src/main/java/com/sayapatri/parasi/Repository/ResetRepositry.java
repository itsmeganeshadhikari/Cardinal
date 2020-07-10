package com.sayapatri.parasi.Repository;

import com.sayapatri.parasi.Model.ResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ResetRepositry extends JpaRepository<ResetToken,Long> {
    ResetToken findByResetToken(String resetToken);


}
