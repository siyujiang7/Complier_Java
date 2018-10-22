/*  Take CS636 for a good time :) */
struct ip6_hdr {
   union {
      struct {
         long ip6_un1_flow; /* 4 bits version, 8 bits TC, 20 bits
                                      flow-ID */
         long ip6_un1_plen; /* payload length */
         long ip6_un1_nxt; /* next header */
         uint8_t ip6_un1_hlim; /* hop limit */
      } ip6_un1;
      uint8_t ip6_un2_vfc; /* 4 bits version, top 4 bits
                                      tclass */
   } ip6_ctlun;
   struct in6_addr ip6_src; /* source address */
   struct in6_addr ip6_dst; /* destination address */
};
