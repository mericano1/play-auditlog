Play Framework audit log
========================

This module is based on [alexanderstrebkov / auditlog module] (https://github.com/alexanderstrebkov/auditlog)

A few features have been added to allow more control on what to audit.

* An annotation (@play.modules.auditlog.Auditable) that allows to mark only the modules that you want audit logs for
* The annotation recordOn field to give you even more control for what operations you want to log. 


Requirements
============
To use the module you can clone this repo and add it to your repositories .In dependencies.yml append:

    - auditlog -> auditlog


    repositories:
     - My modules:
      type:       local
      artifact:   ${application.path}/../[module]
      contains:
          - auditlog


Usage
=====

The usage is quite simple. Let's say you have a model class called Comment and you only want to log when a comment is created.

    @Auditable(recordOn={Operation.CREATE})
    @Entity
    public  class  Comment extends Model  {
    ...
    }

If you want to record create and updates then you would use

    @Auditable(recordOn = {Operation.CREATE, Operation.UPDATE})

well you got the idea.


B
B
B
B
B
 
