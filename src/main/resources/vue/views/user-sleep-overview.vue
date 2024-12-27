<template id="user-sleep-overview">
  <div>
    <h3>Sleeping list </h3>
    <ul>
      <li v-for="sleep in sleeping">
        {{sleep.id}}: {{sleep.name}} for {{sleep.duration}} minutes
      </li>
    </ul>
  </div>
</template>

<script>
app.component("user-sleep-overview",{
  template: "#user-sleep-overview",
  data: () => ({
    sleeping: [],
  }),
  created() {
    const userId = this.$javalin.pathParams["user-id"];
    axios.get(`/api/sleep/${userId}`)
        .then(res => this.sleeping = res.data)
        .catch(() => alert("Error while fetching sleeping"));
  },
  methods: {
    updateSleep: function () {
      const userId = this.$javalin.pathParams["user-id"];
      const url = `/api/sleep/${userId}`
      axios.patch(url,
          {
            name: this.user.name,
            duration: this.user.duration,
            started: this.user.started,

          })
          .then(response =>
              this.user.push(response.data))
          .catch(error => {
            console.log(error)
          })
      alert("Sleep updated!")
    },
    deleteSleep: function () {
      if (confirm("Do you really want to delete?")) {
        const userId = this.$javalin.pathParams["user-id"];
        const url = `/api/sleep/${userId}`
        axios.delete(url)
            .then(response => {
              alert("Sleep deleted")
              //display the /users endpoint
              window.location.href = '/users';
            })
            .catch(function (error) {
              console.log(error)
            });
      }
    }
  }
});
</script>