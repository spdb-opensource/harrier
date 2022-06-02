//校验权限工具类
const authUtil = {
  keys:[],
  fetchAuthUrl(axios, url = "/resource/meta") {
    let ajaxConfig = {
      url: url,
      method: "GET"
    };
    return axios(ajaxConfig)
      .then(response => {
        if(response.data) this.keys = response.data;      
      })
      .catch(error => {
        console.info(error)
        console.log("请求权限异常，请打开控制台查看请求的异常信息！");
      });
  },
  checkUrl(requestConfig,keys) {
    return keys.findIndex(function(value, index, arr) {
      let auth = value.trim().split(":");
        //  console.log(auth);
        //  console.log(requestConfig);
        //  debugger         
      let patt1 = new RegExp(auth[0].replace("/.*","*")); //url pattern
      if (auth[1] == "*") {
        return patt1.test(requestConfig.url);
      } else {
        let patt2 = new RegExp(auth[1]); //url pattern
        return (
          patt1.test(requestConfig.url) &&
          patt2.test(requestConfig.method.toUpperCase())
        );
      }
    }) != -1
      ? true
      : false;
  }
};

export default authUtil;
