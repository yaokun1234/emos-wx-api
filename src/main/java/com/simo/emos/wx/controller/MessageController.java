 package com.simo.emos.wx.controller;

 import com.simo.emos.wx.controller.form.SearchMessageByPageForm;
 import com.simo.emos.wx.dao.entity.MessageEntity;
 import com.simo.emos.wx.dao.entity.User;
 import com.simo.emos.wx.service.MessageService;
 import com.simo.emos.wx.task.MessageTask;
 import com.simo.emos.wx.util.RespBean;
 import com.simo.emos.wx.util.SecurityUtil;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiOperation;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.*;

 import javax.validation.Valid;
 import java.util.HashMap;
 import java.util.List;

 @RestController
@RequestMapping("/message")
@Api("消息模块网络接口")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageTask messageTask;

    @PostMapping("/searchMessageByPage")
    @ApiOperation("获取分页消息列表")
    public RespBean searchMessageByPage(@Valid @RequestBody SearchMessageByPageForm form) {
        User user = SecurityUtil.getCurrentUser();
        int page = form.getPage();
        int length = form.getLength();
        long start = (page - 1) * length;

        List<HashMap> list = messageService.searchMessageByPage(user.getOpenId(), start, length);
        return RespBean.success(list);
    }

    @GetMapping("/searchMessageById")
    @ApiOperation("根据ID查询消息")
    public RespBean searchMessageById(String id) {
        MessageEntity messageEntity = messageService.searchMessageById(id);
        return RespBean.success(messageEntity);
    }

    @PostMapping("/updateUnreadMessage")
    @ApiOperation("未读消息更新成已读消息")
    public RespBean updateUnreadMessage(String id) {
        long rows = messageService.updateUnreadMessage(id);
        return RespBean.success(rows == 1);
    }

    @PostMapping("/deleteMessageRefById")
    @ApiOperation("删除消息")
    public RespBean deleteMessageRefById(String id) {
        boolean flag = messageService.deleteMessageRefById(id);
        return RespBean.success(flag );
    }

    @GetMapping("/refreshMessage")
    @ApiOperation("刷新用户消息")
    public RespBean refreshMessage(){
        User user = SecurityUtil.getCurrentUser();
        messageTask.receiveAsync(user.getOpenId());  // 异步接受消息
        long lastRows=messageService.searchLastCount(user.getOpenId());  // 查询最新消息数量
        long unreadRows=messageService.searchUnreadCount(user.getOpenId());  // 查询未度消息数量
        HashMap<String, String> map = new HashMap<>();
        map.put("lastRows",String.valueOf(lastRows));
        map.put("unreadRows",String.valueOf(unreadRows));
        return RespBean.success(map);
    }

}
