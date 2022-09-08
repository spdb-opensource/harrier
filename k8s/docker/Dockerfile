#基础环境
FROM xxx/openjdk:17.0.2

#环境变量
ENV DOCKER=true
ENV HARRIER_APP=standalone-server

#工作路径
WORKDIR /harrier

#暴露端口
EXPOSE 12345/tcp

#添加源文件
ADD ./harrier  /harrier

#创建镜像处理命令-创建用户 
RUN groupadd  -g 5001 harrier && \ 
    useradd  -u 5001 -d /home/harrier/ -g 5001  harrier && \
    chown -R harrier:harrier /harrier && \
    chmod -R 755 /harrier	


#指定入口
ENTRYPOINT ["su", "harrier", "-c", "/harrier/bin/harrier-daemon.sh start $HARRIER_APP" ]

