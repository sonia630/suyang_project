package cn.jeeweb.modules.sys.service.impl;

import com.o2o.nm.entity.UserInfo;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("passwordService")
public class PasswordService {

	private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	
	@Value(value = "${shiro.credentials.hashAlgorithmName}")
	private String algorithmName = "md5";
	@Value(value = "${shiro.credentials.hashIterations}")
	private final int hashIterations = 2;

	public void encryptPassword(UserInfo user) {
		String newPassword = new SimpleHash(algorithmName, user.getPassword(),
				ByteSource.Util.bytes(user.getName()), hashIterations).toHex();
		user.setPassword(newPassword);
	}
}
