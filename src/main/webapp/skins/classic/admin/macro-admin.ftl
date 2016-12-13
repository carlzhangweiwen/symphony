<#macro admin type>
<#include "../macro-head.ftl">
<!DOCTYPE html>
<html>
    <head>
        <#if type == "index">
        <@head title="${consoleIndexLabel} - ${symphonyLabel}"></@head>
        </#if>
        <#if type == "users">
        <@head title="${userAdminLabel} - ${symphonyLabel}"></@head>
        </#if>
        <#if type == "addUser">
        <@head title="${addUserLabel} - ${symphonyLabel}"></@head>
        </#if>
        <#if type == "articles">
        <@head title="${articleAdminLabel} - ${symphonyLabel}"></@head>
        </#if>
        <#if type == "comments">
        <@head title="${commentAdminLabel} - ${symphonyLabel}">
        <link type="text/css" rel="stylesheet" href="${staticServePath}/js/lib/highlight.js-9.6.0/styles/github.css">
        </@head>
        </#if>
        <#if type == "addDomain">
        <@head title="${addDomainLabel} - ${symphonyLabel}"></@head>
        </#if>
        <#if type == "domains">
        <@head title="${domainAdminLabel} - ${symphonyLabel}"></@head>
        </#if>
        <#if type == "tags">
        <@head title="${tagAdminLabel} - ${symphonyLabel}"></@head>
        </#if>
        <#if type == "addTag">
        <@head title="${addTagLabel} - ${symphonyLabel}"></@head>
        </#if>
        <#if type == "reservedWords">
        <@head title="${reservedWordAdminLabel} - ${symphonyLabel}"></@head>
        </#if>
        <#if type == "addReservedWord">
        <@head title="${allReservedWordLabel} - ${symphonyLabel}"></@head>
        </#if>
        <#if type == "addArticle">
        <@head title="${addArticleLabel} - ${symphonyLabel}"></@head>
        </#if>
        <#if type == "invitecodes">
        <@head title="${invitecodeAdminLabel} - ${symphonyLabel}"></@head>
        </#if>
        <#if type == "ad">
        <@head title="${adAdminLabel} - ${symphonyLabel}"></@head>
        </#if>
        <#if type == "misc">
        <@head title="${miscAdminLabel} - ${symphonyLabel}"></@head>
        </#if>
        <link type="text/css" rel="stylesheet" href="${staticServePath}/css/home.css?${staticResourceVersion}" />
    </head>
    <body>
        <#include "../header.ftl">
        <div class="main">
            <div class="wrapper">
                <#nested>
                <div class="side">
                    <div class="module">
                        <div class="module-header"><h2>${adminLabel}</h2></div> 
                        <div class="module-panel">
                            <nav class="home-menu">
                                <a href="${servePath}/admin"<#if type == "index"> class="current"</#if>>${consoleIndexLabel}</a>
                                <a href="${servePath}/admin/users"<#if type == "users" || type == "addUser"> class="current"</#if>>${userAdminLabel}</a>
                                <a href="${servePath}/admin/articles"<#if type == "articles" || type == "addArticle"> class="current"</#if>>${articleAdminLabel}</a>
                                <a href="${servePath}/admin/comments"<#if type == "comments"> class="current"</#if>>${commentAdminLabel}</a>
                                <a href="${servePath}/admin/domains"<#if type == "domains" || type == "addDomain"> class="current"</#if>>${domainAdminLabel}</a>
                                <a href="${servePath}/admin/tags"<#if type == "tags" || type == "addTag"> class="current"</#if>>${tagAdminLabel}</a>
                                <a href="${servePath}/admin/reserved-words"<#if type == "reservedWords" || type == "addReservedWord"> class="current"</#if>>${reservedWordAdminLabel}</a>
                                <a href="${servePath}/admin/invitecodes"<#if type == "invitecodes"> class="current"</#if>>${invitecodeAdminLabel}</a>
                                <a href="${servePath}/admin/ad"<#if type == "ad"> class="current"</#if>>${adAdminLabel}</a>
                                <a href="${servePath}/admin/misc"<#if type == "misc"> class="current"</#if>>${miscAdminLabel}</a>
                            </nav>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <#include "../footer.ftl">
        <#if type == "comments">
        <script type="text/javascript" src="${staticServePath}/js/settings${miniPostfix}.js?${staticResourceVersion}"></script>
        <script>
            Settings.initHljs();
        </script>
        </#if>
    </body>
</html>
</#macro>
