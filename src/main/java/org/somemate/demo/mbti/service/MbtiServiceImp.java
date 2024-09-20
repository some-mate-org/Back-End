package org.somemate.demo.mbti.service;

import org.somemate.demo.mbti.dao.MbtiDao;
import org.somemate.demo.mbti.dto.Mbti;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class MbtiServiceImp implements MbtiService {
    MbtiDao mbtiDao;
    public MbtiServiceImp(MbtiDao mbtiDao) {this.mbtiDao = mbtiDao;}

    @Override
    public Mbti getMbtiInfo(String mbti) throws SQLException {
        return mbtiDao.getMbtiInfo(mbti);
    }
}
