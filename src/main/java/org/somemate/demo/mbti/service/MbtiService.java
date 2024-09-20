package org.somemate.demo.mbti.service;


import org.somemate.demo.mbti.dto.Mbti;

import java.sql.SQLException;

public interface MbtiService {
    Mbti getMbtiInfo(String mbti) throws SQLException;
}
