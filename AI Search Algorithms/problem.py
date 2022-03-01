# -*- coding: utf-8 -*-
"""
Created on Tue Feb 22 19:02:42 2022

@author: HUAWEI
"""

class Problem:
    
    def __init__(self, initial_state, goal_state=None ):
        self.initial_state = initial_state
        self.goal_state = goal_state
    
    def actions(self,state):
        raise NotImplementedError
        
    
    def result(self,state, action):
        raise NotImplementedError
      
    def is_goal(self,state):
        if(state == self.goal_state):
            return True
        else:
            return False
        
        
    def action_cost(self,state1, action, state2):
        return 1
    
    def h(self,node):
        return 0
  
class RouteProblem(Problem):
    
    def __init__(self, initial_state, goal_state=None, map_graph=None, map_coords=None):
        self.initial_state=initial_state
        self.goal_state=goal_state
        self.map_graph=map_graph
        self.map_coords=map_coords
        
  
        




    
    
class GridProblem(Problem):
    
    def __init__(self, initial_state,N,M,wall_coords,food_coords):
        
        self.N=N
        self.M=M
        self.wall_coords=wall_coords
        self.food_coords=food_coords
        self.initial_state=initial_state
        bool_food_list=[]
        
        i=0
        while i<len(self.food_coords):
            bool_food_list.append(False)
            i+=1
            
        self.food_eaten =tuple(bool_food_list)
        self.initial_state=(self.initial_state,self.food_eaten)
        
       
    def actions(self,state):
            actions = []
            if(state[0]<self.M and (state[0]+1,state[1]) not in self.wall_coords):
                actions.append('up')
            if(state[0]>1 and (state[0]-1,state[1]) not in self.wall_coords):
                actions.append('down')
            if(state[1]<self.N and (state[0],state[1]+1) not in self.wall_coords):
                 actions.append('right')
            if(state[1]>1 and (state[0],state[1]-1) not in self.wall_coords):
                 actions.append('left')     
                 
            return actions

    def result(self, state, action):
        if(action in self.actions(state)):
            if(action =='up'):
                state = (state[0]+1,state[1]) 
            if(action =='down'):
                 state = (state[0]-1,state[1]) 
            if(action =='right'):
                state = (state[0],state[1]+1) 
            if(action =='left'):
                 state = (state[0],state[1]-1) 
        if(state in self.food_coords):
            i=0
            while self.bool_food_list[i] != False:
                i+=1
            self.bool_food_list[i]=True
        return state    
            
    def action_cost(self, state1, action, state2):
        return 1
    
    def is_goal(self, state):
        return all(state[1])
     
    def h(self,node):
       x, y =node.state[0]
       nearst =0
       for i in self.food_eaten:
           if(self.food_eaten==True):
               new = abs(x-self.food_coords[i][0])+abs(y-self.food_coords[i][1])
               if(nearst<new and new != 0):
                   nearst =new
                   
       return nearst            
                   
           
            
            
            
            
            
      
