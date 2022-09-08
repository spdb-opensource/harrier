<template>
     <div class="total" slot="footer">
        总计：<div>{{age}}</div><div>{{score}}</div>
    </div>
</template>
    <script>
       export default{
           props: [list],
        //    props:{
        //      list: {
        //             type:Array,
        //             default:[0,0,0]  //指定默认的值
        //      }
        //    },
           data() {
               return{
                   age: 0,
                   score: 0,
               }
           },
           methods: {
               init: function(){
                console.info(this.list);
                for(var i=0;i<=this.list.length;i++){
                   console.info(this.age +=parseInt(this.list[i].age));
                   console.info(this.score +=parseInt(this.list[i].score))
                }
              },
           },
           mounted: function() {
              this.init();
          }
       }
</script>