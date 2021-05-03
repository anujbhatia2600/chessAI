//public class Evaluation {
//    static int queenMoves[][]={
//            {-20,-10,-10, -5, -5,-10,-10,-20},
//            {-10,  0,  0,  0,  0,  0,  0,-10},
//            {-10,  0,  5,  5,  5,  5,  0,-10},
//            { -5,  0,  5,  5,  5,  5,  0, -5},
//            {  0,  0,  5,  5,  5,  5,  0, -5},
//            {-10,  5,  5,  5,  5,  5,  0,-10},
//            {-10,  0,  5,  0,  0,  0,  0,-10},
//            {-20,-10,-10, -5, -5,-10,-10,-20}};
//    static int midKingMoves[][]={
//            {-30,-40,-40,-50,-50,-40,-40,-30},
//            {-30,-40,-40,-50,-50,-40,-40,-30},
//            {-30,-40,-40,-50,-50,-40,-40,-30},
//            {-30,-40,-40,-50,-50,-40,-40,-30},
//            {-20,-30,-30,-40,-40,-30,-30,-20},
//            {-10,-20,-20,-20,-20,-20,-20,-10},
//            { 20, 20,  0,  0,  0,  0, 20, 20},
//            { 20, 30, 10,  0,  0, 10, 30, 20}};
//    static int endKingMoves[][]={
//            {-50,-40,-30,-20,-20,-30,-40,-50},
//            {-30,-20,-10,  0,  0,-10,-20,-30},
//            {-30,-10, 20, 30, 30, 20,-10,-30},
//            {-30,-10, 30, 40, 40, 30,-10,-30},
//            {-30,-10, 30, 40, 40, 30,-10,-30},
//            {-30,-10, 20, 30, 30, 20,-10,-30},
//            {-30,-30,  0,  0,  0,  0,-30,-30},
//            {-50,-30,-30,-30,-30,-30,-30,-50}};
//    static int bishopMoves[][]={
//            {-20,-10,-10,-10,-10,-10,-10,-20},
//            {-10,  0,  0,  0,  0,  0,  0,-10},
//            {-10,  0,  5, 10, 10,  5,  0,-10},
//            {-10,  5,  5, 10, 10,  5,  5,-10},
//            {-10,  0, 10, 10, 10, 10,  0,-10},
//            {-10, 10, 10, 10, 10, 10, 10,-10},
//            {-10,  5,  0,  0,  0,  0,  5,-10},
//            {-20,-10,-10,-10,-10,-10,-10,-20}};
//    static int knightMoves[][]={
//            {-50,-40,-30,-30,-30,-30,-40,-50},
//            {-40,-20,  0,  0,  0,  0,-20,-40},
//            {-30,  0, 10, 15, 15, 10,  0,-30},
//            {-30,  5, 15, 20, 20, 15,  5,-30},
//            {-30,  0, 15, 20, 20, 15,  0,-30},
//            {-30,  5, 10, 15, 15, 10,  5,-30},
//            {-40,-20,  0,  5,  5,  0,-20,-40},
//            {-50,-40,-30,-30,-30,-30,-40,-50}};
//    static int pawnMoves[][]={
//            { 0,  0,  0,  0,  0,  0,  0,  0},
//            {50, 50, 50, 50, 50, 50, 50, 50},
//            {10, 10, 20, 30, 30, 20, 10, 10},
//            { 5,  5, 10, 25, 25, 10,  5,  5},
//            { 0,  0,  0, 20, 20,  0,  0,  0},
//            { 5, -5,-10,  0,  0,-10, -5,  5},
//            { 5, 10, 10,-20,-20, 10, 10,  5},
//            { 0,  0,  0,  0,  0,  0,  0,  0}};
//    static int rookMoves[][]={
//            { 0,  0,  0,  0,  0,  0,  0,  0},
//            { 5, 10, 10, 10, 10, 10, 10,  5},
//            {-5,  0,  0,  0,  0,  0,  0, -5},
//            {-5,  0,  0,  0,  0,  0,  0, -5},
//            {-5,  0,  0,  0,  0,  0,  0, -5},
//            {-5,  0,  0,  0,  0,  0,  0, -5},
//            {-5,  0,  0,  0,  0,  0,  0, -5},
//            { 0,  0,  0,  5,  5,  0,  0,  0}};
//    public static int evaluate(int noOfMoves, int depth){
//        int counter=0;
//        int piece = evaluatePieces();
//        counter+= (evaluateChances()+evaluatePieces()+evaluateMoves(noOfMoves,depth, piece)+evaluatePositionality(piece));
//        Chess.flipBoard();
//        piece = evaluatePieces();
//        counter-= (evaluateChances()+evaluatePieces()+evaluateMoves(noOfMoves,depth, piece)+evaluatePositionality(piece));
//        Chess.flipBoard();
//        return -(counter+depth*50);
//    }
//    public static int evaluateChances(){
//        int counter = 0;
//        int positionKing = Chess.KingPositionC;
//        for(int i =0;i<64;i++){
//            int row=i/8;
//            int col=i%8;
//            Chess.KingPositionC = i;
//            switch (Chess.Board[row][col]){
//                case "P":
//                {
//                    if(!Chess.safeKing()){
//                        counter -=64;
//                    }
//                }
//                    break;
//                case "K":
//                    if(!Chess.safeKing()){
//                        counter -=300;
//                    }
//                    break;
//                case "Q":
//                    if(!Chess.safeKing()){
//                        counter -=900;
//                    }
//                    break;
//                case "R":
//                    if(!Chess.safeKing()){
//                        counter -=500;
//                    }
//                    break;
//                case "B":
//                    if(!Chess.safeKing()){
//                        counter -=300;
//                    }
//                    break;
//            }
//        }
//        Chess.KingPositionC = positionKing;
//        if(!Chess.safeKing()){
//            counter-=200;
//        }
//        return counter/2;
//    }
//    public static int evaluatePieces(){
//        int counter = 0;
//        int bishops = 0;
//        for(int i =0;i<64;i++){
//            int row=i/8;
//            int col=i%8;
//            switch (Chess.Board[row][col]){
//                case "P":
//                    counter+=100;
//                    break;
//                case "K":
//                    counter+=300;
//                    break;
//                case "Q":
//                    counter+=900;
//                    break;
//                case "R":
//                    counter+=500;
//                    break;
//                case "B":
//                    bishops++;
//                    break;
//            }
//        }
//        if(bishops==2){
//            counter+=300*2;
//        }
//        else if(bishops==1){
//            counter+=250;
//        }
//        return counter;
//    }
//    public static int evaluateMoves(int noofMoves, int depth, int piece){
//        int counter = 0;
//        counter+=noofMoves;
//        if(noofMoves==0){
//            if(!Chess.safeKing()){
//                counter+= -500000*depth; // Checkmate
//            }
//            else{
//                counter+= -300000*depth; // Stalemate
//            }
//        }
//        return counter;
//    }
//    public static int evaluatePositionality(int piece){
//        int counter=0;
//        for (int i=0;i<64;i++){
//            int row = i/8;
//            int col = i%8;
//            switch (Chess.Board[row][col]){
//                case "P":
//                    counter+=pawnMoves[row][col];
//                    break;
//                case "K":
//                    counter+=knightMoves[row][col];
//                    break;
//                case "R":
//                    counter+=rookMoves[row][col];
//                    break;
//                case "B":
//                    counter+=bishopMoves[row][col];
//                    break;
//                case "Q":
//                    counter+=queenMoves[row][col];
//                    break;
//                case "A":
//                    if(piece>=1750){
//                        counter+=midKingMoves[row][col];
//                        counter+=Chess.possibleA(Chess.KingPositionC).length()*10;
//                    }else{
//                        counter+=endKingMoves[row][col];
//                        counter+=Chess.possibleA(Chess.KingPositionC).length()*30;
//                    }
//                    break;
//            }
//        }
//        return counter;
//    }
//}
