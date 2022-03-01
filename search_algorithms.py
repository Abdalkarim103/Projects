# -*- coding: utf-8 -*-
"""
Created on Tue Feb 22 19:03:55 2022

@author: HUAWEI
"""
import heapq
from problem import Problem
from node import Node

def expand(problem, node):
    state = node.state
    for action in problem.actions(state):
        state2 = problem.result(state,action)
        cost = node.path_cost + problem.action_cost(state,action,state2)
        yield Node(state2,node,action,cost)


def get_path_actions(node):
    if node.parent_node is None:
        return []
    elif node is None:
        return []
    else:
        path_actions=[node.action_from_parent]+ get_path_actions(node.parent_node)
        return path_actions


def get_path_states(node):
    if node.parent_node is None:
        return []
    elif node is None:
        return []
    else:
        path_states=[node.state] + get_path_states(node.parent_node) 
        return path_states
    
def best_first_search(problem,f):
    node =Node(state=problem.initial_state)
    frontier = PriorityQueue([node],f)
    reached = {problem.initial_state: node}
    while(frontier.__len__()!=0):
        node=frontier.pop()
        if(problem.is_goal(node.state)):
            return node
        for child in expand(problem,node):
            st1=child.state
            if(st1 not in reached.keys() or child.parent_cost < reached[st1].path_cost):
                reached[st1]=child
                frontier.add(child)
    return None
    
    
    
    
def best_first_search_treelike(problem,f):
    node =Node(state=problem.initial_state)
    frontier = PriorityQueue([node],f)
    while(frontier.__len__()!=0):
        node=frontier.pop()
        if(problem.is_goal(node.state)):
            return node
        for child in expand(problem,node):
            st1=child.state
            frontier.add(child)
    return None
    
    
    
    
def breadth_first_search(problem,treelike=False):
    if treelike:
        return best_first_search_treelike(problem, lambda node: node.depth)
    else:
        return best_first_search(problem, lambda node: node.depth)
    
    
    
def depth_first_search(problem,treelike=False):
    if treelike:
        return best_first_search_treelike(problem, lambda node: -node.depth)
    else:
        return best_first_search(problem, lambda node: -node.depth)
    
    


    
    
    
def uniform_cost_search(problem,treelike=False):
    if treelike:
        return best_first_search_treelike(problem, lambda node: node.path_cost)
    else:
        return best_first_search(problem, lambda node: node.path_cost)
    
    
    
def greedy_search(problem,h,treelike=False):   
    if treelike:
        return best_first_search_treelike(problem, lambda node: h(node))
    else:
        return best_first_search(problem, lambda node: h(node))
    
    
def astar_search(problem,h,treelike=False):    
    if treelike:
        return best_first_search_treelike(problem, lambda node: node.path_cost+h(node))
    else:
        return best_first_search(problem, lambda node: node.path_cost+h(node))
    
    
class PriorityQueue:
    def __init__(self, items=(), priority_function=(lambda x: x)): 
            self.priority_function = priority_function
            self.pqueue = []
            # add the items to the PQ
            for item in items:
                self.add(item)
 
   
    def add(self, item):
          pair = (self.priority_function(item), item)
          heapq.heappush(self.pqueue, pair)

    def pop(self):
        return heapq.heappop(self.pqueue)[1]

    def __len__(self):
        return len(self. pqueue)
    
    
