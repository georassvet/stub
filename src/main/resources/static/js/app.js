
let services = null;
let tasks = null;

$(function(){
    getServices();
    getTasks();
    setInterval(getServices, 15*1000);
})


function getServices(){
    $.get( "/services", function(items) {
        services = items;
        createServices(items);
    })
}

function createServices(items){
    let services = $('#services');
    services.empty();
    $.each(items, function( i, item){
                services.append(createService(item));
    })
}

function createService(item){
    let service = $('<div>' , {
        class: 'service',
        'data-id': item.name
    });

    let name = $('<div>' , {
            class: 'name',
            text: item.name
    });

     let metrics = $('<div>', {
        class: 'metrics'
     });

    metrics.append(createLabeledTag("delay", item.currentDelay)).append(createLabeledTag("rps",  (item.counter/60).toFixed(2)));
    service.append(name).append(metrics);
    return service;
}

function createLabeledTag(label, value){
    let tag = $('<span>', {class:'tag'});
    tag.append(
        $('<span>', {
            class:'label',
            text: label
        }))
       .append(
       $('<span>', {
            class:'value',
            text: value
       }));
    return tag;
}

function getTasks(){
    $.get( "/tasks", function(items) {
            createTasks(items);
    })
}

function createTasks(items){
   let tasks = $('#tasks');
   tasks.empty();

   $.each(items, function( i, item){
        tasks.append(createTask(item));
   })
}

function createTask(item){
    let tr = $('<tr>' , {
        class: 'task',
        'data-id': item.id
    });

     let td_1 = $('<td>' , {
            text: item.service
     });
     let td_2 = $('<td>' , {
                 text: item.startAt
     });
     let td_3 = $('<td>' , {
                 text: item.delay
     });
     let td_4 = $('<td>' , {});
     let btn = $('<button>' , {
                      class: 'btn btn-sm btn-primary delete-task',
                      text: 'Delete',
                      'data-id': item.id
     });
    td_4.append(btn);
    tr.append(td_1).append(td_2).append(td_3).append(td_4);
    return tr;
}

    $(document).on("click",".service",function(e) {
        let $this =  $(this);
        $('.service').removeClass('active');
        $this.addClass('active');
        let element = services[$this.data('id')];

        let request = $('#request');
        let response = $('#response');
        request.empty();
        request.val(element.request);
        response.empty();
        response.val(element.response);

    })

  $(document).on("click",".delete-task",function(e) {
        let $this =  $(this);
        let id = $this.data('id');

        $.post('/tasks/delete', { 'id': id},
            function(){
                $('tr[data-id="' + id + '"]').remove();
            }
        );
  })

  $(document).on("click","#delete-all-tasks",function(e) {
          let $this =  $(this);
          let id = $this.data('id');

          $.post('/deleteAllTasks',
              function(){
                  getTasks();
              }
          );
    })

    $(document).on("click","#clear-delays",function(e) {
            $.post('/clearDelays',
                function(){
                   getServices();
                }
            );
      })

    $(document).on("click","#addDelayTask",function(e) {
            e.preventDefault();
            var form = new FormData($('#form')[0]);
            $.ajax({
                  type: 'post',
                  url: '/addDelayTask',
                  data: form,
                   processData: false,
                   contentType: false,
                   cache: false,
                   success: function (data) {
                        createTasks(data);
                   }
            });
      })






