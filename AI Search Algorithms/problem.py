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
    def __init__(self,initial_state,goal_state=None,map_graph=None,map_coords=None):
        self.initial_state = initial_state
        self.goal_state = goal_state
        self.map_graph = map_graph
        self.map_coords = map_coords

    def actions(self,state):
        action=[]
        for edges in list(self.map_graph.keys()):
            if(state == edges[0]):
                action.append(edges[1])
        return action

    def result(self,state,action):
        actionlist = self.actions(state)
        if(action in actionlist):
            return action
        else:
            return state

    def action_cost(self,state1,action,state2):
        return self.map_graph[(state1, action)]

    def h(self,node):
        if (node==self.goal_state):
            return 0
        else:
            state1 = self.map_coords[node.state]
            state2 = self.map_coords[self.goal_state]
            h2=(state1[0]-state2[0])**2+(state1[1]-state2[1])**2
        return h2**0.5  

    
    
class GridProblem(Problem):
    
    def __init__(self, initial_state,N,M,wall_coords,food_coords):
        
        self.N=N
        self.M=M
        self.wall_coords=wall_coords
        self.food_coords=food_coords
        self.initial_state=initial_state
        self.bool_food_list=[]
        
        i=0
        while i<len(self.food_coords):
            self.bool_food_list.append(False)
            i+=1
            
        self.food_eaten =tuple(self.bool_food_list)
        self.initial_state=(self.initial_state,self.food_eaten)
        
       
    def actions(self,state):
            actions = []
            if(type(state[0]) is tuple):
                m = int(state[0][0])
                n = int(state[0][1])
            else:
                m = int(state[0])
                n = int(state[1])
            if(m < self.M and (m+1,n) not in self.wall_coords):
                actions.append('up')
            if(m > 1 and (m-1,n) not in self.wall_coords):
                actions.append('down')
            if(n < self.N and (m,n+1) not in self.wall_coords):
                 actions.append('right')
            if(n > 1 and (m,n-1) not in self.wall_coords):
                 actions.append('left')     
                 
            return actions

    def result(self, state, action):
        if(type(state[0]) is tuple):
           m = int(state[0][0])
           n = int(state[0][1])
        else:
           m = int(state[0])
           n = int(state[1])
        if(action in self.actions(state)):
            if(action =='up'):
                state = (m+1,n) 
            if(action =='down'):
                 state = (m-1,n) 
            if(action =='right'):
                state = (m,n+1) 
            if(action =='left'):
                 state = (m,n-1) 
        if(state in self.food_coords):
            i=0
            while self.food_eaten[i] != False:
                if(all(self.food_eaten)):
                    break
                i+=1
            s=list(self.food_eaten)
            s[i]=True    
            self.food_eaten=tuple(s)
        return (state,self.food_eaten)    
            
    def action_cost(self, state1, action, state2):
        return 1
    
    def is_goal(self, state):
        return all(state[1])
     
    def h(self,node):
       x = int(node.state[0][0])
       y = int(node.state[0][1])
       nearst = 0
       for i in self.food_eaten:
           if(self.food_eaten[i]==True):
               new = abs(x-self.food_coords[i][0])+abs(y-self.food_coords[i][1])
               if(nearst<new and new != 0):
                   nearst =new
                   
       return nearst
            
            
            
            
      