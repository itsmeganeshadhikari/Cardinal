package com.sayapatri.parasi.Service;

import com.sayapatri.parasi.Model.Notice;
import com.sayapatri.parasi.Repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;
import javax.persistence.OrderBy;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    public void save(Notice notice)
    {
        noticeRepository.save(notice);
    }

    public List<Notice> findAll()
    {
        return noticeRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));

    }



}
