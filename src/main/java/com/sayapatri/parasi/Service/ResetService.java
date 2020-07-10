package com.sayapatri.parasi.Service;

import com.sayapatri.parasi.Model.ResetToken;
import com.sayapatri.parasi.Repository.ResetRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("resetService")
public class ResetService {

    @Autowired
    private ResetRepositry resetRepositry;

    public ResetToken findByResetToken(String resetToken)
    {
        return resetRepositry.findByResetToken(resetToken);
    }
    public void save(ResetToken resetToken)
    {
        resetRepositry.save(resetToken);
    }
}
