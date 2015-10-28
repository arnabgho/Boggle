/**
 * Auto Generated Java Class.
 */
public class BoggleSolver {    
    private int numWords;
    private TrieST<Integer> allwords;
    private TrieSET allValidWords;
    public BoggleSolver(String[] dictionary){
        int i,j;
        allwords=new TrieST<Integer>();
        numWords=dictionary.length;
        for(i=0;i<numWords;i++){
            allwords.put(dictionary[i],i);
        }    
    }
    private void dfs(int startX,int startY,int numrows,int numcols,String[][] QuBoard){
        String[][] tillNow=new String[numrows][numcols];
        int [][] visitedTillNow=new int[numrows][numcols];
        int [][] prevX=new int[numrows][numcols];
        int [][] prevY=new int[numrows][numcols];
        int [][] lastMotion=new int[numrows][numcols];
        int curX=startX,curY=startY;
        
        int i,j,k;
        int[] remI=new int[]{-1,-1,-1,0,0,1,1,1};
        int[] remJ=new int[]{-1,0,1,-1,1,-1,0,1};
        for(i=0;i<numrows;i++){
            for(j=0;j<numcols;j++){
                lastMotion[i][j]=0;
                visitedTillNow[i][j]=0;
                tillNow[i][j]="";
            }
        }
        visitedTillNow[curX][curY]=1;
        while(true){
            boolean found=false;
            for(k=lastMotion[curX][curY];k<8 && !found;k++){
                i=remI[k];j=remJ[k];
                if(curX+i<0 || curX+i>=numrows || curY+j<0 || curY+j>=numcols)
                    continue;
                
//                System.out.println("posX "+(curX+i)+" posY "+(curY+j));
                if(visitedTillNow[curX+i][curY+j]>0)
                    continue;
                String temp=tillNow[curX][curY]+QuBoard[curX+i][curY+j];
                int count=0;
                for(String s: allwords.keysWithPrefix(temp)){
                    count++;break;
                }    
                if(count==0)
                    continue;
                int newCount=0;
                if(temp.length()>=3){
                    for(String s: allwords.keysThatMatch(temp)){
                        newCount++;
                    }
                    if(newCount>0){
                        allValidWords.add(temp);
                    }    
                }
                lastMotion[curX][curY]=k+1;
                tillNow[curX+i][curY+j]=temp;
                visitedTillNow[curX+i][curY+j]=1;
                prevX[curX+i][curY+j]=curX;
                prevY[curX+i][curY+j]=curY;
                found=true;
                curX=curX+i;
                curY=curY+j;
            }
            
            if(!found){
                if(curX==startX && curY==startY)
                    break;
                lastMotion[curX][curY]=0;
                tillNow[curX][curY]="";
                visitedTillNow[curX][curY]=0;
                int tempX=curX,tempY=curY;
                curX=prevX[tempX][tempY];
                curY=prevY[tempX][tempY];
            }
        }
    }
    
    public Iterable<String> getAllValidWords(BoggleBoard board){
        allValidWords=new TrieSET();
        int numrows=board.rows();int numcols=board.cols();
        String[][] QuBoard=new String[numrows][numcols];
        int i,j;
        for(i=0;i<numrows;i++){
            for(j=0;j<numcols;j++){
                char ch=board.getLetter(i,j);
                QuBoard[i][j]=Character.toString(ch);
                if(ch=='Q')
                    QuBoard[i][j]+='U';
            }
        }
        for(i=0;i<numrows;i++){
            for(j=0;j<numcols;j++){
                dfs(i,j,numrows,numcols,QuBoard);
            }
        }
        return allValidWords;
    }
    public int scoreOf(String word){
        int count=0,score=0;
        for(String s: allwords.keysThatMatch(word)){
            count++;
        }    
        if(count==0)
            score=0;
        int l=word.length();
        if(l==3 || l==4)
            score= 1;
        else if(l==5 )
            score= 2;
        else if(l==6 )
            score= 3;
        else if(l==7 )
            score= 5x;
        else if(l>=8)
            score= 11;
        return score;
    }
    
    public static void main(String[] args)
    {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board))
        {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
