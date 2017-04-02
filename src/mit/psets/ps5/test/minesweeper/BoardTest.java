/* Copyright (c) 2007-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

import static org.junit.Assert.*;

import org.junit.Test;

import minesweeper.board.Board;

/**
 * TODO: Description
 */
public class BoardTest {
    
    // TODO: Testing strategy
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // TODO: Tests
    @Test
    public void ctor_SanityTest(){
    	int[][] small = {{1, 0, 0},
    			         {0, 0, 0},
    			         {0, 0, 1}};
    	
    	Board sut = new Board(small);
    }
    
    @Test
    public void toString_initialConfig_allDashes(){
    	int[][] small = {{1, 0, 0},
    			         {0, 0, 0},
    			         {0, 0, 1}};
    	
    	Board sut = new Board(small);
    	String result = sut.toString();
    	
    	assertEquals(String.format("- - -%n- - -%n- - -%n"),result);
    }
    
    @Test
    public void toString_digNonBomb_CountsCorrect(){
    	int[][] small = {{1, 0, 0},
    			         {0, 0, 0},
    			         {0, 0, 1}};
    	
    	Board sut = new Board(small);
    	sut.dig(1, 1);
    	String result = sut.toString();
    	
    	assertEquals(String.format("- - -%n- 2 -%n- - -%n"),result);
    }
    
    @Test
    public void toString_digEmptySpace_Propogates(){
    	int[][] small = {{1, 0, 0},
    			         {0, 0, 0},
    			         {0, 0, 0}};
    	
    	Board sut = new Board(small);
    	sut.dig(2, 2);
    	String result = sut.toString();
    	
    	assertEquals(String.format("- 1  %n1 1  %n     %n"),result);
    }
    
    @Test
    public void toString_digBombSpace_LeavesEmptySpace(){
    	int[][] small = {{1, 0, 0},
    			         {0, 0, 0},
    			         {0, 0, 0}};
    	
    	Board sut = new Board(small);
    	sut.dig(0, 0);
    	String result = sut.toString();
    	
    	assertEquals(String.format("     %n     %n     %n"),result);
    }
    
    @Test
    public void toString_digBombSpace_NeighborCount(){
    	int[][] small = {{1, 1, 0},
    			         {1, 0, 0},
    			         {0, 0, 0}};
    	
    	Board sut = new Board(small);
    	sut.dig(0, 0);
    	String result = sut.toString();
    	
    	assertEquals(String.format("2 - -%n- - -%n- - -%n"),result);
    }
    
    @Test
    public void toString_digBothSpaces_ExpectedState(){
    	int[][] small = {{1, 1, 0},
    			         {1, 0, 0},
    			         {0, 0, 0}};
    	
    	Board sut = new Board(small);
    	sut.dig(0, 0);
    	sut.dig(2, 2);
    	String result = sut.toString();
    	
    	assertEquals(String.format("2 - -%n"
    			                 + "- 2 1%n"
    			                 + "- 1  %n"),result);
    }
    
    @Test
    public void toString_digEmptySpaces_PropogatesAlongEmpty(){
    	int[][] small = {{0, 0, 0, 0, 0},
    			         {0, 0, 0, 0, 0},
    			         {1, 1, 1, 0, 0},
    			         {0, 0, 0, 0, 0},
    			         {0, 0, 0, 0, 0}};
    	
    	Board sut = new Board(small);
    	sut.dig(0, 0);
    	String result = sut.toString();
    	
    	assertEquals(String.format("         %n"
    			                 + "2 3 2 1  %n"
    			                 + "- - - 1  %n"
    			                 + "2 3 2 1  %n"
    			                 + "         %n"),result);
    }
    
    @Test
    public void toString_digTwoSpaces_CorrectState(){
    	int[][] small = {{0, 0, 0, 0, 0},
    			         {0, 0, 0, 0, 0},
    			         {1, 1, 1, 0, 0},
    			         {0, 0, 0, 0, 0},
    			         {0, 0, 0, 0, 0}};
    	
    	Board sut = new Board(small);
    	sut.dig(0, 0);
    	sut.dig(2, 2);
    	String result = sut.toString();
    	
    	assertEquals(String.format("         %n"
    			                 + "2 2 1    %n"
    			                 + "- - 1    %n"
    			                 + "2 2 1    %n"
    			                 + "         %n"),result);
    }
    
    @Test
    public void toString_digAdjacentSpaces_OneIsABomb_ExpectedState(){
    	int[][] small = {{0, 1, 0},
    			         {1, 0, 1},
    			         {0, 1, 0}};
    	
    	Board sut = new Board(small);
    	sut.dig(1, 1);
    	sut.dig(1, 2);
    	String result = sut.toString();
    	
    	assertEquals(String.format("- - -%n"
    			                 + "- 3 -%n"
    			                 + "- 2 -%n"),result);
    }
    
    @Test
    public void toString_digUnknownBombSpace_ReturnFalse(){
    	int[][] small = {{1, 0, 0},
		                 {0, 0, 0},
		                 {0, 0, 0}};
    	
    	Board sut = new Board(small);
    	boolean result = sut.dig(0, 0);
    	
    	assertFalse(result);
    }
    
    @Test
    public void toString_digFlaggedBombSpace_ReturnTrue(){
    	int[][] small = {{1, 0, 0},
		                 {0, 0, 0},
		                 {0, 0, 0}};
    	
    	Board sut = new Board(small);
    	sut.flag(0, 0);
    	boolean result = sut.dig(0, 0);
    	
    	assertTrue(result);
    }
    
    @Test
    public void toString_digUnknownNoBombSpace_ReturnTrue(){
    	int[][] small = {{1, 0, 0},
		                 {0, 0, 0},
		                 {0, 0, 0}};
    	
    	Board sut = new Board(small);
    	boolean result = sut.dig(1, 1);
    	
    	assertTrue(result);
    }
    
    @Test
    public void toString_digUnknownBombSpaceTwice_SecondTimeReturnTrue(){
    	int[][] small = {{1, 0, 0},
		                 {0, 0, 0},
		                 {0, 0, 0}};
    	
    	Board sut = new Board(small);
    	sut.dig(0, 0);
    	boolean result = sut.dig(0, 0);
    	
    	assertTrue(result);
    }
    
    @Test
    public void toString_flagBombSpace_ExpectedState(){
    	int[][] small = {{1, 0, 0},
    			         {0, 0, 0},
    			         {0, 0, 0}};
    	
    	Board sut = new Board(small);
    	sut.flag(0, 0);
    	String result = sut.toString();
    	
    	assertEquals(String.format("F - -%n- - -%n- - -%n"),result);
    }
    
    @Test
    public void toString_flagBombSpaceThenDig_StillFlagged(){
    	int[][] small = {{1, 0, 0},
    			         {0, 0, 0},
    			         {0, 0, 0}};
    	
    	Board sut = new Board(small);
    	sut.flag(0, 0);
    	sut.dig(0, 0);
    	String result = sut.toString();
    	
    	assertEquals(String.format("F - -%n- - -%n- - -%n"),result);
    }
    
    @Test
    public void toString_flagThenDeflagBombSpace_ExpectedState(){
    	int[][] small = {{1, 0, 0},
    			         {0, 0, 0},
    			         {0, 0, 0}};
    	
    	Board sut = new Board(small);
    	sut.flag(0, 0);
    	sut.deflag(0, 0);
    	String result = sut.toString();
    	
    	assertEquals(String.format("- - -%n- - -%n- - -%n"),result);
    }
    
    @Test
    public void toString_FlagBombDigNoBomb_CorrectCounts(){
    	int[][] small = {{1, 0, 0},
    			         {0, 0, 0},
    			         {0, 0, 0}};
    	
    	Board sut = new Board(small);
    	sut.flag(0, 0);
    	sut.dig(1, 1);
    	String result = sut.toString();
    	
    	assertEquals(String.format("F - -%n- 1 -%n- - -%n"),result);
    }
    
    @Test
    public void toString_FlagBombDigBomb_CorrectCounts(){
    	int[][] small = {{1, 0, 0},
    			         {0, 1, 0},
    			         {0, 0, 0}};
    	
    	Board sut = new Board(small);
    	sut.flag(0, 0);
    	sut.dig(1, 1);
    	String result = sut.toString();
    	
    	assertEquals(String.format("F - -%n- 1 -%n- - -%n"),result);
    }
    
    @Test
    public void PublishTest_dig31_RightAnswer(){
    	int[][] seven = {{0, 0, 0, 0, 0, 0, 0},
    			         {0, 0, 0, 0, 1, 0, 0},
    			         {0, 0, 0, 0, 0, 0, 0},
    			         {0, 0, 0, 0, 0, 0, 0},
    			         {0, 0, 0, 0, 0, 0, 0},
    			         {0, 0, 0, 0, 0, 0, 0},
    			         {1, 0, 0, 0, 0, 0, 0}};
    	
    	Board sut = new Board(seven);
    	sut.dig(3,1);
    	String result = sut.toString();
    	
    	assertEquals(String.format("- - - - - - -%n"
    			                 + "- - - 1 - - -%n"
    			                 + "- - - - - - -%n"
    			                 + "- - - - - - -%n"
    			                 + "- - - - - - -%n"
    			                 + "- - - - - - -%n"
    			                 + "- - - - - - -%n"),result);
    }
    
    @Test
    public void toString_digCavity_PropogatesIntoCorners(){
    	int[][] small = {{1, 1, 1, 1, 1},
    			         {1, 0, 0, 0, 1},
    			         {1, 0, 0, 0, 1},
    			         {1, 0, 0, 0, 1},
    			         {1, 1, 1, 1, 1}};
    	
    	Board sut = new Board(small);
    	sut.dig(2,2);
    	String result = sut.toString();
    	
    	assertEquals(String.format("- - - - -%n"
    			                 + "- 5 3 5 -%n"
    			                 + "- 3   3 -%n"
    			                 + "- 5 3 5 -%n"
    			                 + "- - - - -%n"),result);
    }
}
