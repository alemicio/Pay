/* My script made in JavaScript */

setInterval(function() {
    function r(el, deg) {
        el.setAttribute('transform', 'rotate('+ deg +' 50 50)')
    }
    var d = new Date();
    var hours = $("#hourID").val();
    var minutes=$("#minuteID").val();
    if (d.getSeconds()==0) {
       minutes++;
       if (minutes>=60) {
          hours++;
          minutes=0;
          if (hours>=24) {
             hours=0;
          }
          $("#hourID").val(hours);
           $('span[id="hourID_badge"]').text( hours );
           $( "#hourID_slider" ).slider( "option", "value", hours );
       }
       $("#minuteID").val(minutes);
       $('span[id="minuteID_badge"]').text( minutes );
       $( "#minuteID_slider" ).slider( "option", "value", minutes );
    }
    r(sec, 6*d.getSeconds())
    r(min, 6*minutes)
    r(hour, 30*(hours%12) + minutes/2)
}, 1000)