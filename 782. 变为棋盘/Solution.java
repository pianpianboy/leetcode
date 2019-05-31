class Solution {
    public int movesToChessboard(int[][] board) {
        if(board==null||board.length==0||board[0].length==0) return -1;
        
        //取出所有行第一个元素组成一个数组
        int[] rArr = new int[board.length];
        for(int i =0;i<board.length;i++){
            rArr[i] = board[i][0];
        }
        //取出所有列第一个元素组成一个数组
        int[] cArr = board[0];
        
        //检查rArr及cArr中1和0的数量是否不符合规范
        if(checkModel(board)&&check(rArr)&&check(cArr)){
            return minPath(rArr)+minPath(cArr);
        }
        return -1;
    }
    //当1和0的数量差超过1的时候，不可能变为棋盘了
    public boolean check(int[] arr){
        int num0 = 0;
        int num1 =0;
        for(int i=0;i<arr.length;i++){
            if(arr[i]==0)
                num0++;
            else
                num1++;
        }
        if(Math.abs(num0-num1)>1)
            return false;
        else return true;
    }
    //检查行模式和列模式是否符合规范：行只有两种模式，
    //以第一行为基准，检查其余所有的行，这些行要么和第一行相同，要么和第一行相反，否则不可能变为棋盘
    public boolean checkModel(int[][] arr){
        int[] first = arr[0];
        //验证行
        for(int i = 1 ;i<arr.length;i++){
            if(!(isSame(first,arr[i])||isOpsite(first,arr[i])))
                return false;         
        }
        return true;
    }
    
    public boolean isSame(int[] first,int[] arr){
        for(int i =0;i<first.length;i++){
            if(first[i]!=arr[i])
                return false;
        }
        return true;
    }
    
    public boolean isOpsite(int[] first,int[] arr){
         for(int i =0;i<first.length;i++){
            if(first[i]+arr[i]!=1)
                return false;
        }
        return true;
    }
    
    //计算最小移动次数，其实就是计算最小的交换次数
    public int minPath(int[] arr){
        int start = 1;
        int error = 0;
        for(int i=0;i<arr.length;i++){
            if(arr[i]!=start)
                error++;
            start = 1 - start;//让start在0和1之间依次变换
        }
        //根据1和0的数量总为奇偶情况进行讨论
        if(arr.length%2==0){
            return Math.min(error,arr.length-error)>>1;
        }
        else{//当总数为奇数的情况下如（1、1、1、0、0->1、0、1、0、1)，这种情况下，error只可能为偶数的情况
            if(error%2==0)
                return error>>1;
            else//奇数减去技术依然是偶数
                return (arr.length-error)>>1;
        }
    }
}