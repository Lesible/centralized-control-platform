package com.shenhao.oauth2;

import cn.dev33.satoken.oauth2.logic.SaOAuth2Template;
import cn.dev33.satoken.oauth2.model.SaClientModel;
import com.shenhao.data.object.SysOauthClient;
import com.shenhao.mapper.SysOauthClientMapper;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Sa-Token OAuth2.0 整合实现
 */
@Component
public class SaOAuth2TemplateImpl extends SaOAuth2Template {

    private final SysOauthClientMapper sysOauthClientMapper;

    public SaOAuth2TemplateImpl(SysOauthClientMapper sysOauthClientMapper) {
        this.sysOauthClientMapper = sysOauthClientMapper;
    }

    @Override
    public SaClientModel getClientModel(String clientId) {
        SysOauthClient sysOauthClient = sysOauthClientMapper.selectById(clientId);
        if (sysOauthClient == null) {
            return null;
        }
        String authorizedGrantTypes = sysOauthClient.getAuthorizedGrantTypes();
        return new SaClientModel()
                .setClientId(clientId)
                .setClientSecret(sysOauthClient.getClientSecret())
                .setAllowUrl("*")
                .setAccessTokenTimeout(sysOauthClient.getAccessTokenValidity())
                .setRefreshTokenTimeout(sysOauthClient.getRefreshTokenValidity())
                .setContractScope(sysOauthClient.getScope())
                .setIsPassword(authorizedGrantTypes.contains("password"))
                .setIsAutoMode(true);

    }

    // 根据ClientId 和 LoginId 获取openid 
    @Override
    public String getOpenid(String clientId, Object loginId) {
        // 此为模拟数据，真实环境需要从数据库查询 
        return "gr_SwoIN0MC1ewxHX_vfCW3BothWDZMMtx__";
    }

}
