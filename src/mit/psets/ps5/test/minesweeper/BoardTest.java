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
}
