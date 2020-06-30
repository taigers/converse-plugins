# Overview
A converse plugin is a piece of code that enables integration with third party services or functionalities that may not be included in the code product possible. They are built as a standalone library which will be loaded onto the Converse platform via our Converse Connector Engine. Our connector engine manages these plugins and integrates with the Converse platform to help with creating conversation flows.

Plugins are built using Java and should be built as a jar file for Converse to be able to load it. You may use any third party libraries to assist your business logic implementation.

Follow the steps below to create a new plugin. As an example, we will first create a service action that simply adds two numbers.
