import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;

public class Chess {
    static String Board[][] = {
            {"r","k","b","q","a","b","k","r"},
            {"p","p","p","p","p","p","p","p"},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {"P","P","P","P","P","P","P","P"},
            {"R","K","B","Q","A","B","K","R"}
    };
    static int KingPositionC, KingPositionL, maxDepth=4;
    static int whitePlayer = -1;
    static JFrame frame;
    public static void main(String[] args) {
        while(!("A".equals(Board[KingPositionC/8][KingPositionC%8]))){KingPositionC++;}
        while(!("a".equals(Board[KingPositionL/8][KingPositionL%8]))){KingPositionL++;}
        frame = new JFrame("Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UI ui = new UI();
        frame.add(ui);
        frame.setSize(500,500);
        frame.setVisible(true);
        System.out.println(movesPossible());
        Object[] option = {"Computer","Human"};
        whitePlayer = JOptionPane.showOptionDialog(null,"Who would be moving first?","Player option",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null, option,option[1]);
        if(whitePlayer==0){
            makeMove(alphaBeta(maxDepth,-1000000,1000000,"",0));
            flipBoard();
            frame.repaint();
        }
        //makeMove(alphaBeta(maxDepth,-1000000,100000,"",0));
        for (int i =0; i<8;i++){
            System.out.println(Arrays.toString(Board[i]));
        }
    }
    public static String alphaBeta(int depth, int alpha, int beta, String move, int player){
         String list= movesPossible();
         if(depth==0 || list.length()==0){
             return move + (Evaluation.evaluate(list.length())*(1-player*2));
         }
         player = 1-player;
         for(int i=0;i<list.length();i+=5){
            makeMove(list.substring(i,i+5));
            flipBoard();
            String str = alphaBeta(depth-1,alpha,beta, list.substring(i,i+5),player);
            int estimate = Integer.valueOf(str.substring(5));
            flipBoard();
            undoMove(list.substring(i,i+5));
            if(player==0){
                if(estimate<=beta){
                    beta=estimate;
                    if(depth==maxDepth){
                        move=str.substring(0,5);
                    }
                }
            }else{
                if(estimate>alpha){
                    alpha=estimate;
                    if(depth==maxDepth){
                        move=str.substring(0,5);
                    }
                }
            }
            if(alpha>=beta){
                if (player==0){
                    return move+beta;
                }
                else{
                    return move+alpha;
                }
            }
         }
        if (player==0){
            return move+beta;
        }
        else{
            return move+alpha;
        }
    }

    public static void flipBoard(){
        String temp;
        for( int i = 0 ; i<32;i++){
            int row = i/8;
            int col = i%8;
            if(Character.isUpperCase(Board[row][col].charAt(0))){
                temp= Board[row][col].toLowerCase();
            }else{
                temp= Board[row][col].toUpperCase();
            }
            if(Character.isUpperCase(Board[7-row][7-col].charAt(0))){
                Board[row][col]=Board[7-row][7-col].toLowerCase();
            }else{
                Board[row][col]=Board[7-row][7-col].toUpperCase();
            }
            Board[7-row][7-col]=temp;
        }
        int Ktemp = KingPositionC;
        KingPositionC = 63- KingPositionL;
        KingPositionL = 63-Ktemp;
    }
    public static void makeMove(String move){
        if(move.charAt(4)!='P'){
            Board[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))]=Board[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))];
            Board[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))]=" ";
            if("A".equals(Board[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))])){
                KingPositionC = 8*Character.getNumericValue(move.charAt(2))+Character.getNumericValue(move.charAt(3));
            }
        }
        else{
            Board[1][Character.getNumericValue(move.charAt(0))]=" ";
            Board[0][Character.getNumericValue(move.charAt(1))]=String.valueOf(move.charAt(3));
        }

    }
    public static void undoMove(String move){
        if(move.charAt(4)!='P'){
            Board[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))]=Board[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))];
            Board[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))]=String.valueOf(move.charAt(4));
            if("A".equals(Board[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))])){
                KingPositionC = 8*Character.getNumericValue(move.charAt(0))+Character.getNumericValue(move.charAt(1));
            }
        }
        else{
            Board[1][Character.getNumericValue(move.charAt(0))]="P";
            Board[0][Character.getNumericValue(move.charAt(1))]=String.valueOf(move.charAt(2));
        }

    }
    public static String movesPossible(){
        String list = "";
        for (int i=0;i<64;i++){
            switch(Board[i/8][i%8]){
                case "P":
                    list+=possibleP(i);
                    break;
                case "R":
                    list+=possibleR(i);
                    break;
                case "K":
                    list+=possibleK(i);
                    break;
                case "B":
                    list+=possibleB(i);
                    break;
                case "Q":
                    list+=possibleQ(i);
                    break;
                case "A":
                    list+=possibleA(i);
                    break;
            }
        }
        return list;
    }
    public static String movement(int row, int col, int newRow, int newCol, String swapper){
        String old = Board[newRow][newCol];
        Board[row][col]=" ";
        Board[newRow][newCol]=swapper;
        String list = "";
        if(safeKing()){
            list = ""+row+col+newRow+newCol+old;
        }
        Board[row][col]=swapper;
        Board[newRow][newCol]=old;
        return list;
    }

    public static String possibleP(int i) {
        String list = "";
        String oldPiece;
        int row = i / 8;
        int col = i % 8;
        for (int j = -1; j <= 1; j += 2) {
            try {
                if (Character.isLowerCase(Board[row - 1][col + j].charAt(0)) && i >= 16) {
                    String l = movement(row,col,row-1,col+j,"P");
                    if(l.length()>0){
                        list=list+l;
                    }
                }
            } catch (Exception e) {
            }
            try {
                if (Character.isLowerCase(Board[row - 1][col + j].charAt(0)) && i < 16) {
                    String[] temp = {"Q", "R", "B", "K"};
                    for (int k = 0; k < 4; k++) {
                        oldPiece = Board[row - 1][col + j];
                        Board[row][col] = " ";
                        Board[row - 1][col + j] = temp[k];
                        if (safeKing()) {
                            list = list + col + (col + j) + oldPiece + temp[k]+"P";
                        }
                        Board[row][col] = "P";
                        Board[row - 1][col + j] = oldPiece;
                    }
                }

            } catch (Exception e) {
            }
        }
        try {
            if(" ".equals(Board[row-1][col]) && i>=16){
                String l = movement(row,col,row-1,col,"P");
                if(l.length()>0){
                    list=list+l;
                }
            }
        } catch (Exception e) {
        }
        try {
            if(" ".equals(Board[row-1][col]) && i<16){
                String[] temp = {"Q", "R", "B", "K"};
                for (int k = 0; k < 4; k++) {
                    oldPiece = Board[row - 1][col];
                    Board[row][col] = " ";
                    Board[row - 1][col] = temp[k];
                    if (safeKing()) {
                        list = list + col + col + oldPiece + temp[k]+"P";
                    }
                    Board[row][col] = "P";
                    Board[row - 1][col] = oldPiece;
                }
            }
        } catch (Exception e) {
        }
        try {
            if(" ".equals(Board[row-1][col]) && " ".equals(Board[row-2][col])&& i>=48){
                String l = movement(row,col,row-2,col,"P");
                if(l.length()>0){
                    list=list+l;
                }
            }
        } catch (Exception e) {
        }
        return list;
    }
    public static String possibleQ(int i){
        String list = "";
        String oldPiece = "";
        int row = i/8;
        int col = i%8;
        int temp = 1;
        for(int j =-1;j<=1;j++){
            for(int k =-1;k<=1;k++){
                if(j!=0 || k!=0){
                    try{
                        while(" ".equals(Board[row+temp*j][col+temp*k])){
                            String l = movement(row,col,row+temp*j,col+temp*k,"Q");
                            if(l.length()>0){
                                list=list+l;
                            }
                            temp++;
                        }
                        if(Character.isLowerCase(Board[row+temp*j][col+temp*k].charAt(0))){
                            String l = movement(row,col,row+temp*j,col+temp*k,"Q");
                            if(l.length()>0){
                                list=list+l;
                            }
                        }
                    }
                    catch (Exception e){

                    }
                    temp=1;
                }
            }
        }
        return list;
    }

    public static String possibleR(int i){
        String list = "";
        int row = i/8;
        int col = i%8;
        int temp = 1;
        for(int j=-1;j<=1;j+=2){
            try{
                while(" ".equals(Board[row][col+temp*j])){
                    String l = movement(row,col,row,col+temp*j,"R");
                    if(l.length()>0){
                        list=list+l;
                    }
                    temp++;
                }
                if(Character.isLowerCase(Board[row][col+temp*j].charAt(0))){
                    String l = movement(row,col,row,col+temp*j,"R");
                    if(l.length()>0){
                        list=list+l;
                    }
                }
            }
            catch (Exception e){
            }
            temp = 1;
            try{
                while(" ".equals(Board[row+temp*j][col])){
                    String l = movement(row,col,row+temp*j,col,"R");
                    if(l.length()>0){
                        list=list+l;
                    }
                    temp++;
                }
                if(Character.isLowerCase(Board[row+temp*j][col].charAt(0))){
                    String l = movement(row,col,row+temp*j,col,"R");
                    if(l.length()>0){
                        list=list+l;
                    }
                }
            }
            catch (Exception e){
            }
            temp=1;
        }
        return list;
    }
    public static String possibleB(int i){
        String list = "";
        int row = i/8;
        int col = i%8;
        int temp = 1;
        for(int j =-1;j<=1;j+=2){
            for(int k =-1;k<=1;k+=2){
                try{
                    while(" ".equals(Board[row+temp*j][col+temp*k])){
                        String l = movement(row,col,row+temp*j,col+temp*k,"B");
                        if(l.length()>0){
                            list=list+l;
                        }
                        temp++;
                    }
                    if(Character.isLowerCase(Board[row+temp*j][col+temp*k].charAt(0))){
                        String l = movement(row,col,row+temp*j,col+temp*k,"B");
                        if(l.length()>0){
                            list=list+l;
                        }
                    }
                }
                catch (Exception e){
                }
                temp=1;
            }
        }
        return list;
    }
    public static String possibleA(int i){
        String list = "";
        int row = i/8;
        int col = i%8;
        for(int j = 0; j<9;j++){
            if(j!=4){
                try{
                    if(Character.isLowerCase(Board[row-1+j/3][col-1+j%3].charAt(0)) || " ".equals(Board[row-1+j/3][col-1+j%3])){
                        int temp = KingPositionC;
                        KingPositionC  = i+(j/3)*8+j%3-9;
                        String l = movement(row,col,row-1+j/3,col-1+j%3,"A");
                        if(l.length()>0){
                            list=list+l;
                        }
                        KingPositionC = temp;
                    }
                }
                catch (Exception e){}
            }
        }
        return list;
    }

    public static String possibleK(int i){
        String list = "";
        int row = i/8;
        int col = i%8;
        for(int j =-1;j<=1;j+=2){
            for(int k =-1;k<=1;k+=2){
                try{
                    if(" ".equals(Board[row+j][col+k*2])||Character.isLowerCase(Board[row+j][col+k*2].charAt(0))){
                        String l = movement(row,col,row+j,col+k*2,"K");
                        if(l.length()>0){
                            list=list+l;
                        }
                    }
                }
                catch (Exception e){
                }
                try{
                    if(" ".equals(Board[row+j*2][col+k])||Character.isLowerCase(Board[row+2*j][col+k].charAt(0))){
                        String l = movement(row,col,row+j*2,col+k,"K");
                        if(l.length()>0){
                            list=list+l;
                        }
                    }
                }
                catch (Exception e){

                }
            }
        }
        return list;
    }
    public static boolean safeKing(){
        int temp=1;
        for(int i =-1;i<=1;i+=2){
            for(int j =-1;j<=1;j+=2){
                try{
                    while(" ".equals(Board[KingPositionC/8+temp*i][KingPositionC%8+temp*j])){
                        temp++;
                    }
                    if("b".equals(Board[KingPositionC/8+temp*i][KingPositionC%8+temp*j]) || "q".equals(Board[KingPositionC/8+temp*i][KingPositionC%8+temp*j])){
                        return false;
                    }
                }
                catch (Exception e){
                }
                temp = 1;
            }
        }
        for(int i =-1;i<=1;i+=2){
            try{
                while(" ".equals(Board[KingPositionC/8][KingPositionC%8+temp*i])){temp++;}
                if("r".equals(Board[KingPositionC/8][KingPositionC%8+temp*i]) || "q".equals(Board[KingPositionC/8][KingPositionC%8+temp*i])){
                    return false;
                }
            }
            catch (Exception e){
            }
            temp=1;
            try{
                while(" ".equals(Board[KingPositionC/8+temp*i][KingPositionC%8]))
                {
                    temp++;
                }
                if("r".equals(Board[KingPositionC/8+temp*i][KingPositionC%8]) || "q".equals(Board[KingPositionC/8+temp*i][KingPositionC%8])){
                    return false;
                }
            }
            catch (Exception e){
            }
            temp = 1;
        }
        for(int i =-1;i<=1;i+=2){
            for(int j =-1;j<=1;j+=2){
                try{
                    if("k".equals(Board[KingPositionC/8+i][KingPositionC%8+j*2])){
                        return false;
                    }
                }
                catch (Exception e){
                }
                try{
                    if("k".equals(Board[KingPositionC/8+i*2][KingPositionC%8+j])){
                        return false;
                    }
                }
                catch (Exception e){
                }
            }
        }
        if(KingPositionC>=16){
            try{
                if("p".equals(Board[KingPositionC/8-1][KingPositionC%8-1])){
                    return false;
                }
            }
            catch (Exception e){
            }
            try{
                if("p".equals(Board[KingPositionC/8-1][KingPositionC%8+1])){
                    return false;
                }
            }
            catch (Exception e){
            }
            for(int i =-1;i<=1;i++){
                for(int j =-1;j<=1;j++){
                    if(i!=0 || j!=0){
                        try{
                            if("a".equals(Board[KingPositionC/8+i][KingPositionC%8+j])){
                                return false;
                            }
                        }
                        catch (Exception e){
                        }
                    }
                }
            }
        }
        return true;
    }
    public static void GameOver(){
        JOptionPane.showMessageDialog(null,"CheckMate! Game Over! Please quit the game");
    }
    public static void StaleMate(){
        JOptionPane.showMessageDialog(null,"StaleMate! Game Over! Please quit the game");
    }
}
class Evaluation {
    static int queenMoves[][]={
            {-20,-10,-10, -5, -5,-10,-10,-20},
            {-10,  0,  0,  0,  0,  0,  0,-10},
            {-10,  0,  5,  5,  5,  5,  0,-10},
            { -5,  0,  5,  5,  5,  5,  0, -5},
            {  0,  0,  5,  5,  5,  5,  0, -5},
            {-10,  5,  5,  5,  5,  5,  0,-10},
            {-10,  0,  5,  0,  0,  0,  0,-10},
            {-20,-10,-10, -5, -5,-10,-10,-20}};
    static int midKingMoves[][]={
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-20,-30,-30,-40,-40,-30,-30,-20},
            {-10,-20,-20,-20,-20,-20,-20,-10},
            { 20, 20,  0,  0,  0,  0, 20, 20},
            { 20, 30, 10,  0,  0, 10, 30, 20}};
    static int endKingMoves[][]={
            {-50,-40,-30,-20,-20,-30,-40,-50},
            {-30,-20,-10,  0,  0,-10,-20,-30},
            {-30,-10, 20, 30, 30, 20,-10,-30},
            {-30,-10, 30, 40, 40, 30,-10,-30},
            {-30,-10, 30, 40, 40, 30,-10,-30},
            {-30,-10, 20, 30, 30, 20,-10,-30},
            {-30,-30,  0,  0,  0,  0,-30,-30},
            {-50,-30,-30,-30,-30,-30,-30,-50}};
    static int bishopMoves[][]={
            {-20,-10,-10,-10,-10,-10,-10,-20},
            {-10,  0,  0,  0,  0,  0,  0,-10},
            {-10,  0,  5, 10, 10,  5,  0,-10},
            {-10,  5,  5, 10, 10,  5,  5,-10},
            {-10,  0, 10, 10, 10, 10,  0,-10},
            {-10, 10, 10, 10, 10, 10, 10,-10},
            {-10,  5,  0,  0,  0,  0,  5,-10},
            {-20,-10,-10,-10,-10,-10,-10,-20}};
    static int knightMoves[][]={
            {-50,-40,-30,-30,-30,-30,-40,-50},
            {-40,-20,  0,  0,  0,  0,-20,-40},
            {-30,  0, 10, 15, 15, 10,  0,-30},
            {-30,  5, 15, 20, 20, 15,  5,-30},
            {-30,  0, 15, 20, 20, 15,  0,-30},
            {-30,  5, 10, 15, 15, 10,  5,-30},
            {-40,-20,  0,  5,  5,  0,-20,-40},
            {-50,-40,-30,-30,-30,-30,-40,-50}};
    static int pawnMoves[][]={
            { 0,  0,  0,  0,  0,  0,  0,  0},
            {50, 50, 50, 50, 50, 50, 50, 50},
            {10, 10, 20, 30, 30, 20, 10, 10},
            { 5,  5, 10, 25, 25, 10,  5,  5},
            { 0,  0,  0, 20, 20,  0,  0,  0},
            { 5, -5,-10,  0,  0,-10, -5,  5},
            { 5, 10, 10,-20,-20, 10, 10,  5},
            { 0,  0,  0,  0,  0,  0,  0,  0}};
    static int rookMoves[][]={
            { 0,  0,  0,  0,  0,  0,  0,  0},
            { 5, 10, 10, 10, 10, 10, 10,  5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            { 0,  0,  0,  5,  5,  0,  0,  0}};
    public static int evaluate(int noOfMoves){
        int counter=0;
        int piece = evaluatePieces();
        counter+= (evaluateChances()+piece+evaluateMoves(noOfMoves)+evaluatePositionality(piece));
        Chess.flipBoard();
        piece = evaluatePieces();
        counter-= (evaluateChances()+piece+evaluateMoves(noOfMoves)+evaluatePositionality(piece));
        Chess.flipBoard();
        return (counter);
    }
    public static int evaluateChances(){
        int counter = 0;
        int positionKing = Chess.KingPositionC;
        for(int i =0;i<64;i++){
            int row=i/8;
            int col=i%8;
            Chess.KingPositionC = i;
            switch (Chess.Board[row][col]){
                case "P":
                {
                    if(!Chess.safeKing()){
                        counter -=64;
                    }
                }
                break;
                case "K":
                    if(!Chess.safeKing()){
                        counter -=300;
                    }
                    break;
                case "Q":
                    if(!Chess.safeKing()){
                        counter -=900;
                    }
                    break;
                case "R":
                    if(!Chess.safeKing()){
                        counter -=500;
                    }
                    break;
                case "B":
                    if(!Chess.safeKing()){
                        counter -=300;
                    }
                    break;
            }
        }
        Chess.KingPositionC = positionKing;
        if(!Chess.safeKing()){
            counter-=200;
        }
        return counter/2;
    }
    public static int evaluatePieces(){
        int counter = 0;
        int bishops = 0;
        for(int i =0;i<64;i++){
            int row=i/8;
            int col=i%8;
            switch (Chess.Board[row][col]){
                case "P":
                    counter+=100;
                    break;
                case "K":
                    counter+=300;
                    break;
                case "Q":
                    counter+=900;
                    break;
                case "R":
                    counter+=500;
                    break;
                case "B":
                    bishops++;
                    break;
            }
        }
        if(bishops==2){
            counter+=300*2;
        }
        else if(bishops==1){
            counter+=250;
        }
        return counter;
    }
    public static int evaluateMoves(int noofMoves){
        int counter = 0;
        counter+=noofMoves;
        if(noofMoves==0){
            if(!Chess.safeKing()){
                counter+= -500000; // Checkmate
            }
            else{
                counter+= -300000; // Stalemate
            }
        }
        return counter;
    }
    public static int evaluatePositionality(int piece){
        int counter=0;
        for (int i=0;i<64;i++){
            int row = i/8;
            int col = i%8;
            switch (Chess.Board[row][col]){
                case "P":
                    counter+=pawnMoves[row][col];
                    break;
                case "K":
                    counter+=knightMoves[row][col];
                    break;
                case "R":
                    counter+=rookMoves[row][col];
                    break;
                case "B":
                    counter+=bishopMoves[row][col];
                    break;
                case "Q":
                    counter+=queenMoves[row][col];
                    break;
                case "A":
                    if(piece>=1750){
                        counter+=midKingMoves[row][col];
                        counter+=Chess.possibleA(Chess.KingPositionC).length()*10;
                    }else{
                        counter+=endKingMoves[row][col];
                        counter+=Chess.possibleA(Chess.KingPositionC).length()*30;
                    }
                    break;
            }
        }
        return counter;
    }
}

class UI extends JPanel implements MouseListener, MouseMotionListener {
    static int square = 32;
    static int originalX;
    static int originalY;
    static int updatedX;
    static int updatedY;


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setBackground(Color.yellow);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        for(int i =0;i<64;i+=2){
            g.setColor(new Color(255,200,100));
            g.fillRect((i%8+(i/8)%2)*square,(i/8)*square,square,square);
            g.setColor(new Color(150,50,30));
            g.fillRect(((i+1)%8-((i+1)/8)%2)*square,((i+1)/8)*square,square,square);
        }
        Image chessPieces;
        chessPieces = new ImageIcon("./ChessPieces.png").getImage();
        for(int i=0;i<64;i++){
            int j = -1;
            int k = -1;
            switch (Chess.Board[i/8][i%8]){
                case "P":
                    j =5;
                    k =0;
                    break;
                case"p":
                    j = 5;
                    k=1;
                    break;
                case "R":
                    j =2;
                    k =0;
                    break;
                case"r":
                    j =2;
                    k=1;
                    break;
                case "K":
                    j =4;
                    k =0;
                    break;
                case"k":
                    j =4;
                    k=1;
                    break;
                case "B":
                    j = 3;
                    k =0;
                    break;
                case"b":
                    j =3;
                    k=1;
                    break;
                case "Q":
                    j =1;
                    k =0;
                    break;
                case"q":
                    j =1;
                    k=1;
                    break;
                case "A":
                    j =0;
                    k =0;
                    break;
                case"a":
                    j =0;
                    k=1;
                    break;
            }
            if(j!=-1 && k!=-1){
                g.drawImage(chessPieces,(i%8)*square,(i/8)*square,(i%8+1)*square,(i/8+1)*square,j*64,k*64,(j+1)*64,(k+1)*64,this);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
//        System.out.println("Pressing");
        if(e.getX()<8*square && e.getY()<8*square ){
            originalX = e.getX();
            originalY=e.getY();
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        System.out.println("Released");
        if(e.getX()<8*square && e.getY()<8*square ){
            updatedX = e.getX();
            updatedY=e.getY();
            if(e.getButton()==MouseEvent.BUTTON1){
                String drag;
                if(updatedY/square==0 && originalY/square==1 && "P"==Chess.Board[originalY/square][originalX/square]){
                    drag=""+originalX/square+updatedX/square+Chess.Board[updatedY/square][updatedX/square]+"QP";
                }
                else{
                    drag=""+originalY/square+originalX/square+updatedY/square+updatedX/square+Chess.Board[updatedY/square][updatedX/square];
                }
                String userMoves=Chess.movesPossible();
//                System.out.println("Printing");
                System.out.println(userMoves);
//                for (int i=0;i<8;i++) {
//                    System.out.println(Arrays.toString(AlphaBetaChess.chessBoard[i]));
//                }
                if (userMoves.replaceAll(drag, "").length()<userMoves.length()) {
                    //if valid move
                    Chess.makeMove(drag);
                    long startTime = System.currentTimeMillis();
                    Chess.flipBoard();
                    Chess.makeMove(Chess.alphaBeta(Chess.maxDepth, -1000000, 1000000, "", 0));
                    Chess.flipBoard();
                    long endTime = System.currentTimeMillis();
                    if(Chess.movesPossible().length()==0){
                        if(!Chess.safeKing()){
                            repaint();
                            Chess.GameOver();
                        }
                        else{
                            repaint();
                            Chess.StaleMate();
                        }
                    }
                    long timeElapsed = endTime - startTime;
                    System.out.println("Execution time in milliseconds : " + timeElapsed );
                    repaint();
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}

