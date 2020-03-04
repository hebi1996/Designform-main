package com.design.system.service;

import com.design.system.domain.UserDO;
import com.design.system.vo.UserVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {
	UserDO get(Long id);
	List<UserDO> list(Map<String, Object> map);
	int count(Map<String, Object> map);
	int save(UserDO user);
	int update(UserDO user);
	int remove(Long userId);
	int batchremove(Long[] userIds);
	boolean exit(Map<String, Object> params);
	int resetPwd(UserVO userVO, UserDO userDO) throws Exception;
	int adminResetPwd(UserVO userVO) throws Exception;
	int updatePersonal(UserDO userDO);
    Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId) throws Exception;
}
