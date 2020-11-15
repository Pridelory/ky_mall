package com.wangmeng.mall.api.util;

import com.wangmeng.mall.model.UmsMember;
import lombok.extern.slf4j.Slf4j;
import com.wangmeng.mall.common.api.ResultCode;
import com.wangmeng.mall.common.exception.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author mtcarpenter
 * @github https://github.com/mtcarpenter/mall-cloud-alibaba
 * @desc 微信公众号：山间木匠
 */
@Slf4j
@Component
public class MemberUtil {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${redis.member}")
    private String member;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取 redis 中用户信息
     *
     * @return
     */
    public UmsMember getRedisUmsMember(HttpServletRequest request) {
        // token 验证
        String token = request.getHeader(tokenHeader);
        if (StringUtils.isBlank(token)) {
            log.error("token = {}", token);
            throw new ApiException(ResultCode.UNAUTHORIZED);
        }
        String username = jwtTokenUtil.getUserNameFromToken(token);
        if (StringUtils.isBlank(username)) {
            log.error("resultToken = {}", username);
            throw new ApiException(ResultCode.UNAUTHORIZED);
        }
        return (UmsMember) redisTemplate.opsForValue().get(username);
    }
}
